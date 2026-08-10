package ru.mealcard.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ConfigServiceTest {
    @Test
    void singletoneTest() {
        assertSame(Config.getInstance(), Config.getInstance());
    }

    @Test
    void readProperties() {
        Config config = Config.getInstance();

        assertEquals(8080, config.getPort());
        assertEquals("Europe/Moscow", config.getZone());
        assertEquals("windows-1251", config.getCharset());
        assertEquals("out/cards", config.getOutputDir());
    }
}
