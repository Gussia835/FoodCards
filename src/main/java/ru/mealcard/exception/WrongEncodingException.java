package ru.mealcard.exception;

public class WrongEncodingException extends RuntimeException {
    public WrongEncodingException(String message) {
        super(message);
    }
}
