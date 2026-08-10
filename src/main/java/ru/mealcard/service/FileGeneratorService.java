package ru.mealcard.service;

import ru.mealcard.Base;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileService extends Base {

    private static final Path output_dir = Path.of(getConfig().getOutputDir());
    private static final String charset = getConfig().getCharset();

    private static final FileService instance = new FileService();

    public static FileService getInstance() {
        return instance;
    }

    private FileService() {
        try {
            Files.createDirectories(output_dir);
            info("directory create");
        } catch (IOException e) {
            error("cant create directories {}", e.getMessage(), e);
        }
    }

    public void save(String filename, String content) {
        try {
            Files.write(output_dir.resolve(filename),
                        content.getBytes(Charset.forName(charset)));
            info("file created {}", filename);
        } catch (IOException e) {
            error("cant create file with content", e.getMessage(), e);
            throw new IllegalStateException("File saved error", e);
        }
    }
}
