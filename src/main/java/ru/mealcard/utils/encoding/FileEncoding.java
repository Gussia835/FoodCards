package ru.mealcard.utils.encoding;

import lombok.Getter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public enum Encoding {
    UTF_8("UTF-8", StandardCharsets.UTF_8),
    UTF_16("UTF-16",StandardCharsets.UTF_16),
    WINDOWS_1251("Windows-1251",Charset.forName("windows-1251")),
    KOI8_R("KOI8-R", Charset.forName("KOI8-R"));

    @Getter
    private final String name;
    @Getter private final Charset charset;

    Encoding(String name, Charset charset) {
        this.name = name;
        this.charset = charset;
    }

    public static Encoding fromName(String s) {
        for (Encoding e : values())
            if (e.name.equalsIgnoreCase(s)) return e;
        throw new IllegalArgumentException("Unknown encoding: " + s);
    }
}
