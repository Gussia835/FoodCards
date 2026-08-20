package ru.mealcard.service.format;

import java.nio.charset.Charset;
import java.nio.file.Path;

public interface Visitor<T> {
    Charset getCharset() ;
    void visit(Path target, T data);
}