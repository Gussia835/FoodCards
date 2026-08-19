package ru.mealcard.config;

import lombok.Getter;
import ru.mealcard.Base;
import ru.mealcard.utils.config.PropertyKeys;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertyLoader extends Base {

  @Getter
  private static final PropertyLoader instance = new PropertyLoader();

    private static final String EXTERNAL_FILE = "app.properties";
    private static final String CLASSPATH_FILE = PropertyKeys.FILE_PROPERTY;

    private PropertyLoader() {}


    public void load(Properties target) {
        Path external = Paths.get(EXTERNAL_FILE);

        if (Files.exists(external)) {
            try (InputStream in = Files.newInputStream(external)) {
                target.load(in);
                info("Loaded config from external file: {}", external.toAbsolutePath());
                return;
            } catch (IOException e) {
                warn("Failed to read external config, falling back to classpath: {}", e.getMessage());
            }
        }

        try (InputStream in = PropertyLoader.class.getResourceAsStream(CLASSPATH_FILE)) {
            if (in == null) {
                throw new IllegalStateException("Property file not found: " + CLASSPATH_FILE);
            }
            target.load(in);
            info("Loaded config from classpath (defaults)");
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load config", e);
        }
    }
}