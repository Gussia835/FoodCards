package ru.mealcard.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.mealcard.Base;
import ru.mealcard.dto.RequestDTO;
import ru.mealcard.dto.ResponseDTO;
import ru.mealcard.service.GenerateService;
import ru.mealcard.service.ResponseService;

import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GenerateHandler extends Base implements HttpHandler {

    private final GenerateService generateService = GenerateService.getInstance();
    private final ResponseService responses = ResponseService.getInstance();
    private final ExecutorService executorService;

    public GenerateHandler(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void handle(HttpExchange exchange) {
        try {
            if (!"POST".equals(exchange.getRequestMethod())) {
                responses.sendError(exchange, HttpURLConnection.HTTP_BAD_METHOD, "Method not allowed");
                return;
            }
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            debug("raw body: {}", body);

            RequestDTO request = objectMapper.readValue(body, RequestDTO.class);
            executorService.submit(() -> process(exchange, request));
        } catch (Exception e) {
            error("Handler error: {}", e.getMessage(), e);
            responses.sendError(exchange, HttpURLConnection.HTTP_INTERNAL_ERROR, "Internal server error");
        }
    }

    private void process(HttpExchange exchange, RequestDTO request) {
        try {
            ResponseDTO response = generateService.process(request);
            responses.sendJson(exchange, HttpURLConnection.HTTP_OK, response);
        } catch (IllegalArgumentException e) {
            responses.sendError(exchange, HttpURLConnection.HTTP_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            error("Process error: {}", e.getMessage(), e);
            responses.sendError(exchange, HttpURLConnection.HTTP_INTERNAL_ERROR, "Internal server error");
        }
    }
}