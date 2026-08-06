package ru.mealcard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.mealcard.models.TypeProcedure;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public final class HeaderDTO {
    private final LocalDateTime sheduledTime;
    private final TypeProcedure procType;
    private final LocalDateTime sendAt;
}
