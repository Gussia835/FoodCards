package ru.mealcard.service;

import lombok.Getter;
import ru.mealcard.Base;
import ru.mealcard.dto.BodyDTO;
import ru.mealcard.dto.HeaderDTO;
import ru.mealcard.dto.TrailerDTO;
import ru.mealcard.format.Visitor;

import java.util.List;

public class FileContent extends Base {

    @Getter
    private final HeaderDTO header;
    @Getter
    private  final List<BodyDTO> bodies;

    public FileContent(HeaderDTO headerDTO, List<BodyDTO> bodies) {
        this.header = headerDTO;
        this.bodies = bodies;
    }


    public String render(Visitor visitor) {
        StringBuilder sb = new StringBuilder();
        sb.append(header.accept(visitor))
                .append("\r\n");

        debug("header is rendered");

        for (BodyDTO body : bodies) {
            sb.append(body.accept(visitor))
                    .append("\r\n");
        }

        debug("bodies is rendered");

        TrailerDTO trailer = new TrailerDTO(bodies.size());
        sb.append(trailer.accept(visitor));

        debug("trailer is rendered");

        return sb.toString();
    }

}
