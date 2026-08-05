package ru.mealcard.config;

import ru.mealcard.Base;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigService extends Base {
    private static final String FILE_PROPERTY =  "/app.properties";
    private static final String PORT_KEY = "port";
    private static final String OUTPUT_DIR_KEY = "output-dir";
    private static final String ZONE_KEY = "zone";
    private static final String CHARSET_KEY = "charset";

    private static final ConfigService instance = new ConfigService();
    private final Properties properties = new Properties();


    private ConfigService() {
        try (InputStream inputStream = ConfigService.class.getResourceAsStream(FILE_PROPERTY)) {
            if (inputStream == null) {
                error("failed founding property-file");
                throw new IllegalStateException("not found property file");
            }

            properties.load(inputStream);
            info("load property-file");

        } catch (IOException e) {
            error("failed load config: {}", e);
        }
    }

    public static ConfigService getInstance() {
        return instance;
    }

    private String get(String key) {
        String value = instance.properties.getProperty(key);
        return value == null ? "" : value.trim();
    }

    public int getPort() {
        info("Получаем порт из конфига");
        return Integer.parseInt(get(PORT_KEY));

    }

    public String getZone() {
        info("Получаем зону из конфига");
        return get(ZONE_KEY);
    }

    public String getCharset() {
        info("Получаем кодировку из конфига");
        return get(CHARSET_KEY);
    }

    public String getOutputDir() {
        info("Получаем директорию файлов из конфига");
        return get(OUTPUT_DIR_KEY);
    }
}

