package ru.mealcard.testConfig;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ConfigServiceTest {
    @Test
    void singletoneTest() {
        assertSame(ConfigService.getInstance(), ConfigService.getInstance());
    }

    @Test
    void readProperties() {
        ConfigService config = ConfigService.getInstance();

        assertEquals(8080, config.getPort());
        assertEquals("Europe/Moscow", config.getZone());
        assertEquals("windows-1251", config.getCharset());
        assertEquals("out/cards", config.getOutputDir());
    }
}
