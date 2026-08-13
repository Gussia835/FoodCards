package ru.mealcard.service.format;

import java.nio.file.Path;

public interface Visitor<T> {
    void visit(Path target, T data);
}