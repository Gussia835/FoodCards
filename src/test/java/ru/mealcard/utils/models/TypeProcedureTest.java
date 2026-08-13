package ru.mealcard.models;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.mealcard.utils.models.TypeProcedure;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TypeProcedureTest {

    @ParameterizedTest
    @CsvSource({"IN-TIME", "IMMEDIATE"})
    void testTypeProcedureFromCode(String code) {
        assertEquals(TypeProcedure.valueOf(code.replace("-", "_")),
                    TypeProcedure.fromCode(code));
    }

    @ParameterizedTest
    @CsvSource({"time", "myTime"})
    void testIncorrectProcedureFromCode(String code) {
        assertEquals(TypeProcedure.IMMEDIATE, TypeProcedure.fromCode(code));
    }
}
