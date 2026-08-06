package ru.mealcard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.StringBufferInputStream;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDTO {
    private String status;
    private String filename;
    private String content;
}
