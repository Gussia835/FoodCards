package ru.mealcard.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mealcard.utils.error.ErrorFileType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {
    private String filename;
    private ErrorFileType errorType;
    private int lineCount;
    private String bankCode;
    private String branchCode;
    private String nameSystem;
}