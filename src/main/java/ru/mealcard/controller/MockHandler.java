package ru.mealcard.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.mealcard.Base;
import ru.mealcard.dto.RequestDTO;
import ru.mealcard.dto.ResponseDTO;
import ru.mealcard.service.GenerateService;
import ru.mealcard.service.MockDataService;
import ru.mealcard.service.ResponseService;

import java.net.HttpURLConnection;
import java.util.concurrent.ExecutorService;

public class MockHandler extends Base implements HttpHandler {

    private final GenerateService generateService = GenerateService.getInstance();
    private final MockDataService mockDataService = MockDataService.getInstance();
    private final ResponseService responseService = ResponseService.getInstance();

    private final int count_mock = getConfig().getMockDefaultCount();

    private final ExecutorService executorService;

    public MockHandler(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void handle(HttpExchange exchange) {
        try {
            RequestDTO request = mockDataService.generateRequest(count_mock);
            executorService.submit(() -> process(exchange, request));
        } catch (Exception e) {
            error("Mock error: {}", e.getMessage(), e);
            responseService.sendError(exchange, HttpURLConnection.HTTP_INTERNAL_ERROR, "Internal exception");
        }
    }


    private void process(HttpExchange exchange, RequestDTO request) {
        try {
            ResponseDTO response = generateService.process(request);
            responseService.sendJson(exchange, HttpURLConnection.HTTP_OK, response);
        } catch (IllegalArgumentException e) {
            responseService.sendError(exchange, HttpURLConnection.HTTP_BAD_REQUEST, e.getMessage());
        }
    }

}
