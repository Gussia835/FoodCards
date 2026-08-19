package ru.mealcard.service.send.models;

import java.nio.file.Path;
import java.util.Map;

public interface Sender {
    public ResultSending send(Path file, Map<String, String> metadata);
}
