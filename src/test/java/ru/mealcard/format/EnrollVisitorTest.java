//package ru.mealcard.format;
//
//import org.junit.jupiter.api.Test;
//import ru.mealcard.models.TypeOperation;
//import ru.mealcard.models.TypeProcedure;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class EnrollVisitorTest {
//    private final Visitor visitor = new EnrollVisitor();
//
//    @Test
//    void HeaderVisitorTest() {
//        HeaderDTO headerDTO = new HeaderDTO(null, TypeProcedure.IMMEDIATE,
//                LocalDateTime.of(2026, 8, 6, 12, 30, 45));
//
//        String result = visitor.visit(headerDTO);
//        assertEquals(42, result.length());
//        assertTrue(result.startsWith("H 20260806 123045 IMMEDIATE"));
//    }
//
//    @Test
//    void BodyVisitorTest() {
//        BodyDTO bodyDTO = new BodyDTO("Иванов", "1000401000050000",
//                                    TypeOperation.DR, 11);
//        String result = visitor.visit(bodyDTO);
//
//        assertEquals(152, result.length());
//        assertEquals("DR", result.substring(130, 132), "TYPE must be at 131-132");
//        assertEquals("11", result.substring(132).trim(), "SUMM must be at 133-152");
//    }
//
//    @Test
//    void trailerLengthMustBe20() {
//        TrailerDTO trailerDTO = new TrailerDTO(5);
//        String result = visitor.visit(trailerDTO);
//
//        assertEquals(20, result.length());
//        assertTrue(result.startsWith("T"));
//        assertTrue(result.endsWith("5"));
//    }
//}
//