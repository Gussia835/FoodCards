package ru.mealcard.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.mealcard.config.Config;
import ru.mealcard.service.utils.FilenameGeneratorUtil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilenameGeneratorUtilTest {

    private final FilenameGeneratorUtil generator = FilenameGeneratorUtil.getInstance();
    private final Pattern pattern = Pattern.compile("Z(\\d{3})(\\d{3})\\.GLAER_ENROLL\\1\\2(\\d+)\\.(\\d{3})");

    @ParameterizedTest
    @CsvSource({"001,032", "1,32", "999,999"})
    void bankAndBranchPadded(String bank, String branch) {
        String name = generator.generate(bank, branch, "GLAER");
        Matcher m = pattern.matcher(name);
        assertTrue(m.matches());
        assertEquals(String.format("%03d", Integer.parseInt(bank)), m.group(1));
        assertEquals(String.format("%03d", Integer.parseInt(branch)), m.group(2));
    }

    @Test
    void julianDateIsToday() {
        String name = generator.generate("001", "032", "GLAER");
        int julian = LocalDate.now(ZoneId.of(Config.getInstance().getZone())).getDayOfYear();
        assertTrue(name.endsWith(String.format(".%03d", julian)));
    }

    @Test
    void sequenceIncrements() {
        Matcher first = pattern.matcher(generator.generate("001", "032", "GLAER"));
        Matcher second = pattern.matcher(generator.generate("001", "032", "GLAER"));
        assertTrue(first.matches() && second.matches());
        assertEquals(Integer.parseInt(first.group(3)) + 1, Integer.parseInt(second.group(3)));
    }
}