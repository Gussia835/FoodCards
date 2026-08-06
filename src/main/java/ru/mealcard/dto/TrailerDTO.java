package ru.mealcard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.mealcard.format.Visitor;

@Getter
@AllArgsConstructor
public final class TrailerDTO {
    private final int count;

    public String accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
