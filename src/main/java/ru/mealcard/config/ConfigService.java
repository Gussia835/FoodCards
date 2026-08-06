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

    private static final String BANK_CODE_KEY = "bank-code";
    private static final String BRANCH_CODE_KEY = "branch-code";
    private static final String AES_NAME_KEY = "aes-name";
    private static final String MOCK_COUNT_KEY = "mock-count";

    private static final ConfigService instance = new ConfigService();
    private final Properties properties = new Properties();


    private ConfigService() {
        try (InputStream inputStream = ConfigService.class.getResourceAsStream(FILE_PROPERTY)) {
            if (inputStream == null) {
                error("failed founding property-file");
                throw new IllegalStateException("not found property file");
            }

            properties.load(inputStream);
            debug("load property-file");

        } catch (IOException e) {
            error("failed load config: {}", e);
            throw new IllegalStateException("Config load failed", e);
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
        debug("Получаем порт из конфига");
        return Integer.parseInt(get(PORT_KEY));

    }

    public String getZone() {
        debug("getting zone from config");
        return get(ZONE_KEY);
    }

    public String getCharset() {
        debug("charset from config");
        return get(CHARSET_KEY);
    }

    public String getOutputDir() {
        debug("getting output dir from config");
        return get(OUTPUT_DIR_KEY);
    }

    public String getBankCode() {
        return get(BANK_CODE_KEY);
    }
    public String getBranchCode() {
        return get(BRANCH_CODE_KEY);
    }
    public String getAesName() {

        return get(AES_NAME_KEY);
    }

    public int getMockDefaultCount() {
        return Integer.parseInt(get(MOCK_COUNT_KEY));
    }
}

