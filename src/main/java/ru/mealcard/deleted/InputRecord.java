package ru.mealcard.dto;

import java.time.LocalDateTime;

public record InputRecord(String fio, String account, String type, int sum,
                          LocalDateTime sheduledTime, String procType) {
    public InputRecord(String fio, String account, String type, int sum) {
        this(fio, account, type, sum, LocalDateTime.now(), "IMMEDIATE");
    }
}
