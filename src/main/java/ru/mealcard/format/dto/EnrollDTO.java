package ru.mealcard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mealcard.models.TypeOperation;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollDTO {
    private String fio;
    private String account;
    private TypeOperation type;
    private int summ;
}