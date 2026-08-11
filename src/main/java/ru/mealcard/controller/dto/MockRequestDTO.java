package ru.mealcard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MockRequestDTO {
    String bankCode;
    String branchCode;
    String nameSystem;
    int rowCount;
    int fileCount;


}
