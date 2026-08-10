package ru.mealcard.controller.utils;

import com.sun.net.httpserver.HttpExchange;
import ru.mealcard.Base;

public class RequestConverter extends Base {
    public static <T> T convert(HttpExchange exchange, T clazz) {
        byte[] bytes = exchange.getRequestBody().readAllBytes();
        T requestDTO = objectMapper.readValue(bytes, clazz);
        return requestDTO;
    }
}
