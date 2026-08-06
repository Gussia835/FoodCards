package ru.mealcard.format;

import ru.mealcard.dto.BodyDTO;
import ru.mealcard.dto.HeaderDTO;
import ru.mealcard.dto.TrailerDTO;

public interface Visitor {
    public String visit(HeaderDTO header);
    public String visit(BodyDTO body);
    public String visit(TrailerDTO trailer);
}
