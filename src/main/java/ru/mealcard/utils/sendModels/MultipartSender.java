package ru.mealcard.service.send.models;

import ru.mealcard.Base;

import java.net.http.HttpClient;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

public class MultipartSender extends Base implements Sender {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String sendTo;

    public MultipartSender(String sendTo) {
        this.sendTo = sendTo;
    }

    @Override
    public ResultSending send(Path file, Map<String, String> metadata) {
        String boundary = "----" + UUID.randomUUID();

        
    }
}
