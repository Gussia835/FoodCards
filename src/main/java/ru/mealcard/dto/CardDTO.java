package ru.mealcard.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class CardDTO {
    private String fio;
    private String account;
    private String type;
    private int sum;
    private LocalDateTime sheduledDateTime;
    private String procType = "IMMEDIATE";
}
