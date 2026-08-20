package ru.mealcard.utils.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import ru.mealcard.utils.encoding.FileEncoding;

public enum ErrorFileType {
    EMPTY(null),
    SPACES(null),
    NEWLINES(null),
    UTF_16(FileEncoding.UTF_16),
    WINDOWS_1251(FileEncoding.WINDOWS_1251),
    KOI8_R(FileEncoding.KOI8_R);

    @Getter
    private final FileEncoding encoding;

    ErrorFileType(FileEncoding encoding) {
        this.encoding = encoding;
    }

    @JsonCreator
    public static ErrorFileType fromCode(String s) {
        for (ErrorFileType t : values())
            if (t.name().equalsIgnoreCase(s)) return t;
        throw new IllegalArgumentException("Unknown error type: " + s);
    }
}
