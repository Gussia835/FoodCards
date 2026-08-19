package ru.mealcard.service.send.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mealcard.utils.sendModels.TypeSend;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendRequestDTO {
    private String filename;
    private TypeSend typeSend;
    private Map<String, String> meta;
}
