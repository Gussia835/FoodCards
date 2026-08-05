package ru.mealcard;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mealcard.config.ConfigService;

public class Base {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected static final ObjectMapper mapper = createMapper();

    private ObjectMapper createMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return objectMapper;
    }

    protected static ConfigService getConfig() {
        return ConfigService.getInstance();
    }

    protected void error(String text, Object... args) {
        this.logger.error(text, args);
    }

    protected void info(String text, Object... args) {
        logger.info(text, args);
    }

    protected void warn(String text, Object... args) {
        logger.warn(text, args);
    }

    protected void debug(String text, Object... args) {
        logger.debug(text, args);
    }
}
