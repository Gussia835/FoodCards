package ru.mealcard;

import com.sun.net.httpserver.HttpServer;
import ru.mealcard.config.ConfigService;
import ru.mealcard.controller.GenerateHandler;
import ru.mealcard.controller.MockHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        int port = ConfigService.getInstance().getPort();

        ExecutorService executor = Executors.newSingleThreadExecutor();

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

            server.createContext("/generate", new GenerateHandler(executor));
            server.createContext("/mock", new MockHandler(executor));

            server.setExecutor(Executors.newSingleThreadExecutor());

            server.start();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            executor.shutdown();
            return;
        }
        System.out.println("Server started on port " + port);
    }
}