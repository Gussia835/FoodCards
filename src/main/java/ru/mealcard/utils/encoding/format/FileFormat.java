package ru.mealcard.utils.format;

import lombok.Getter;
import ru.mealcard.utils.encoding.FileEncoding;

public enum FileFormat {

    GLAER_ENROLL("glaer_enroll", FileEncoding.WINDOWS_1251);


    @Getter
    private final String code;

    @Getter
    private final FileEncoding encoding;

    FileFormat(String code, FileEncoding enc) {
        this.code = code;
        this.encoding = enc;

    }


    public static FileFormat fromCode(String s) {
        for (FileFormat f : values())
            if (f.code.equalsIgnoreCase(s)) {

                return f;
            }

        throw new IllegalArgumentException("Unknown format: " + s);

    }

}

