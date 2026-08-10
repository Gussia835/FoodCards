package ru.mealcard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.mealcard.models.TypeProcedure;

import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.List;

@Getter
@AllArgsConstructor
public final class DataForEnrollDTO {
    private final ZonedDateTime sendAt;
    private final ZonedDateTime scheduledDateTime;
    private final TypeProcedure procType;
    private final Iterable<EnrollDTO> records;

}