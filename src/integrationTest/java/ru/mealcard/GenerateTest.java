package ru.mealcard;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.linesOf;
import static org.hamcrest.Matchers.*;

public class GenerateTest extends IntegrationTest {

    private static String generateValidBody(ZonedDateTime dateTime) {

        if (dateTime != null) {
            return """
                    {
                      "bankCode": "001",
                      "branchCode": "032",
                      "nameSystem": "GLAER",
                      "procType": "IN-TIME",
                      "processAt": "%s",
                      "cards": [
                        {"fio": "Иванов Иван Ивнаович", "account": "1111111111111111", "type": "DR", "summ": 1500},
                        {"fio": "Петров Петр Петрович", "account": "2111111111111111", "type": "ZR", "summ": 1600}
                      ]
                    }
                    """.formatted(dateTime);
        } else {
            return """
                    {
                      "bankCode": "001",
                      "branchCode": "032",
                      "nameSystem": "GLAER",
                      "procType": "%s",
                      "cards": [
                          {"fio": "Иванов Иван Ивнаович", "account": "1111111111111111", "type": "DR", "summ": 1500},
                          {"fio": "Петров Петр Петрович", "account": "2111111111111111", "type": "ZR", "summ": 1600}
                      ]
                    }
                    """.formatted("IMMEDIATE");
        }
    }

    @Test
    void testValidImmediateRequest() {
        String filename = given()
                .contentType(ContentType.JSON)
                .body(generateValidBody(null))

                .when()
                .post("/generate")

                .then()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("filename", notNullValue())
                .body("filename", matchesPattern("Z001032\\.GLAER_ENROLL001032\\d+\\.\\d{3}"))

                .extract()
                .path("filename");

        Path file = outputDir.resolve(filename);
        assertThat(file).exists();

        List<String> lines = linesOf(file);
        assertThat(lines).hasSize(4);
        assertThat(lines.get(0)).hasSize(42).startsWith("H ");
        assertThat(lines.get(1)).hasSize(152);
        assertThat(lines.get(2)).hasSize(152);
        assertThat(lines.get(3)).hasSize(20).startsWith("T").endsWith("2");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"bankCode\":\"12ab\",\"branchCode\":\"032\",\"nameSystem\":\"GLAER\",\"procType\":\"IMMEDIATE\",\"cards\":[{\"fio\":\"A\",\"account\":\"1000401000050000\",\"type\":\"DR\",\"summ\":1}]}",
            "{\"bankCode\":\"001\",\"branchCode\":\"032\",\"nameSystem\":\"GLAER\",\"procType\":\"IMMEDIATE\",\"cards\":[]}",
            "{\"bankCode\":\"001\",\"branchCode\":\"032\",\"nameSystem\":\"GLAER\",\"procType\":\"IN-TIME\",\"cards\":[{\"fio\":\"A\",\"account\":\"1000401000050000\",\"type\":\"DR\",\"summ\":1}]}",
            "{broken json"
    })
    void testInvalidBodyRequest(String body) {
        given()
                .contentType(ContentType.JSON)
                .body(body)

                .when()
                .post("/generate")

                .then()
                .statusCode(400)
                .body("status", equalTo("ERROR"));
    }

    @Test
    void testWrongMethod() {
        given()
                .contentType(ContentType.JSON)
                .body(generateValidBody(null))

                .when()
                .get("/generate")

                .then()
                .statusCode(405);
    }

}
