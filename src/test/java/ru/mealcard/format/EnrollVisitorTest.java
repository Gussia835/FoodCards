package ru.mealcard.format;

import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.mealcard.exception.FileGenerationException;
import ru.mealcard.service.format.EnrollVisitor;
import ru.mealcard.service.format.dto.DataForEnrollDTO;
import ru.mealcard.service.format.dto.EnrollDTO;
import ru.mealcard.utils.models.TypeOperation;
import ru.mealcard.utils.models.TypeProcedure;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnrollVisitorTest {

    @TempDir
    Path tempDir;

    private final EnrollVisitor visitor = new EnrollVisitor();

    private final Faker faker = new Faker();

    private ZonedDateTime sendAt;

    @BeforeEach
    void setUp() {
        sendAt = ZonedDateTime.of(2026, 8, 11, 10, 0, 0, 0, ZoneId.of("Europe/Moscow"));
    }

    private DataForEnrollDTO data(TypeProcedure proc, ZonedDateTime scheduled, Iterable<EnrollDTO> records) {
        return new DataForEnrollDTO(sendAt, scheduled, proc, records);
    }

    private EnrollDTO record(String fio, TypeOperation op, int summ) {
        return new EnrollDTO(fio, "1" + faker.number().digits(15), op, summ);
    }

    private String[] lines(Path file) throws Exception {
        String content = Files.readString(file);

        assertTrue(content.endsWith("\r\n"));
        return content.split("\r\n", -1);
    }

    @Test
    void testFixedWidthLines() throws Exception {
        Path file = tempDir.resolve("test.txt");

        visitor.visit(file, data(TypeProcedure.IMMEDIATE, null,
                List.of(record(faker.name().fullName(),
                                faker.options().option(TypeOperation.CR, TypeOperation.DR, TypeOperation.ZR),
                                100),
                        record(faker.name().fullName(),
                                faker.options().option(TypeOperation.CR, TypeOperation.DR, TypeOperation.ZR),
                                faker.number().numberBetween(10, 100_000)))));

        String[] lines = lines(file);
        assertEquals(42, lines[0].length());
        assertEquals(152, lines[1].length());
        assertEquals(152, lines[2].length());
        assertEquals(20, lines[3].length());
        assertTrue(lines[0].startsWith("H "));
        assertTrue(lines[3].startsWith("T"));
        assertTrue(lines[3].endsWith("2"));
    }

    @Test
    void testLongFioTruncatedTo100() throws Exception {
        Path file = tempDir.resolve("trunc.txt");
        visitor.visit(file, data(TypeProcedure.IMMEDIATE, null,
                List.of(record("Ф".repeat(150), TypeOperation.DR, 5))));
        assertEquals(152, lines(file)[1].length());
    }

    @Test
    void testVisitToInvalidPathThrows() {
        Path file = tempDir.resolve("no-such-dir/file.txt");
        assertThrows(FileGenerationException.class,
                () -> visitor.visit(file, data(TypeProcedure.IMMEDIATE, null, List.of())));
    }

}