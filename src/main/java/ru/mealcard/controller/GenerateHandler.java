package ru.mealcard.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.mealcard.Base;
import ru.mealcard.exception.FileGenerationException;
import ru.mealcard.exception.InvalidRequestException;
import ru.mealcard.utils.RequestConverterUtil;
import ru.mealcard.service.dto.GenerateRequestDTO;
import ru.mealcard.service.generate.GenerateService;
import ru.mealcard.service.ResponseService;

import java.net.HttpURLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

public class GenerateHandler extends Base implements HttpHandler {

    private final GenerateService service = GenerateService.getInstance();
    private final ResponseService responses = ResponseService.getInstance();
    private final ExecutorService executorService;

    public GenerateHandler(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void handle(HttpExchange exchange) {
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            responses.sendError(exchange, HttpURLConnection.HTTP_BAD_METHOD, "Method not allowed");
            exchange.close();
            return;
        }

        try {
            executorService.submit(() -> process(exchange));
        } catch (RejectedExecutionException e) {
            error("Thread pool rejected task: {}", e.getMessage());
            responses.sendError(exchange, HttpURLConnection.HTTP_UNAVAILABLE, "Server busy");
            exchange.close();
        }
    }

    private void process(HttpExchange exchange) {
        try {
            GenerateRequestDTO requestDTO = RequestConverterUtil.parseBody(exchange, GenerateRequestDTO.class);
            var response = service.process(requestDTO);
            responses.sendJson(exchange, HttpURLConnection.HTTP_OK, response);
        } catch (InvalidRequestException e) {
            error("incorrect request: {}", e.getMessage(), e);
            responses.sendError(exchange, HttpURLConnection.HTTP_BAD_REQUEST, e.getMessage());
        } catch (FileGenerationException e) {
            error("File generation failed: {}", e.getMessage(), e);
            responses.sendError(exchange, HttpURLConnection.HTTP_INTERNAL_ERROR, "Generation failed");
        } catch (Exception e) {
            error("Unexpected error: {}", e.getMessage(), e);
            responses.sendError(exchange, HttpURLConnection.HTTP_INTERNAL_ERROR, "Internal server error");
        } finally {
            exchange.close();
        }
    }
}