package ru.mealcard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.mealcard.models.TypeOperation;

@AllArgsConstructor
@Getter
public final class BodyDTO {
    private final String fio;
    private final String account;
    private final TypeOperation type;
    private final int summ;
}
