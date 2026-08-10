package ru.mealcard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.mealcard.models.TypeOperation;

@Getter
@Setter
@AllArgsConstructor
public final class EnrollDTO {
    private final String fio;
    private final String account;
    private final TypeOperation type;
    private final int summ;
}