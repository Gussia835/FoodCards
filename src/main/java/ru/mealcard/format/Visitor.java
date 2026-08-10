package ru.mealcard.format;

import java.nio.file.Path;

public interface Visitor<T> {
    void visit(Path target, T data);
}