package ru.mealcard.utils.models;

import lombok.Getter;

public enum TypeSend {
    MULTIPART("multipart"),
    CHUNK("chunk"),
    GRPC("grpc");

    @Getter
    private String code;

    TypeSend(String code) {

        this.code = code;
    }

    public static TypeSend fromCode(String s) {
        for (TypeSend t : values()) {
            if (t.code.equalsIgnoreCase(s)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown type of send: " + s);
    }


}
