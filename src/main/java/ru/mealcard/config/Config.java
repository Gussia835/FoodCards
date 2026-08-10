package ru.mealcard.config;

import ru.mealcard.Base;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Properties;

public class Config extends Base {

    private static final Properties PROPERTIES = new Properties();

    private static final Config INSTANCE = new Config();



    private Config() {
        try (InputStream inputStream = Config.class.getResourceAsStream(PropertyKeys.FILE_PROPERTY)) {
            if (inputStream == null) {
                error("failed founding property-file");
                throw new IllegalStateException("not found property file");
            }

            PROPERTIES.load(inputStream);
            debug("load property-file");

        } catch (IOException e) {
            error("failed load config: {}", e);
            throw new IllegalStateException("Config load failed", e);
        }
    }

    public static Config getInstance() {

        return INSTANCE;
    }

    private String get(String key, String def) {
        return (String) PROPERTIES.getOrDefault(key, def);
    }

    public int getPort() {
        debug("Получаем порт из конфига");
        return Integer.parseInt(get(PropertyKeys.PORT_KEY, "8081"));

    }

    public String getZone() {
        debug("getting zone from config");
        return get(PropertyKeys.ZONE_KEY, "Europe/Moscow");
    }

    public Charset getCharset() {
        debug("charset from config");
        return Charset.forName(get(PropertyKeys.CHARSET_KEY, "UTF-8"));
    }

    public Path getOutputDir() {
        debug("getting output dir from config");
        return Path.of(get(PropertyKeys.OUTPUT_DIR_KEY, "./out/cards"));
    }

    public int getPoolSize() {
        return Integer.parseInt(get(PropertyKeys.POOL_SIZE_KEY, "2"));
    }
}

