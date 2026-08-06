package ru.mealcard.models;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TypeProcedure {
    IN_TIME("IN-TIME"),
    IMMEDIATE("IMMEDIATE");

    private final String code;

    public String getCode() {
        return code;
    }

    public static TypeProcedure fromCode(String code) {
        switch (code.toUpperCase()) {
            case "IMMEDIATE" -> {
                return IMMEDIATE;
            }
            case "IN-TIME" -> {
                return IN_TIME;
            }
            default -> throw new IllegalArgumentException("Unknown PROC_TYPE");
        }
    }

}
