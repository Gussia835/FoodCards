package ru.mealcard.service;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import ru.mealcard.Base;

import java.time.LocalDate;
import java.time.ZoneId;

public class FilenameService extends Base {

    private final String bankCode = StringUtils.leftPad(getConfig().getBankCode(), 3, '0');
    private final String branchCode = StringUtils.leftPad(getConfig().getBranchCode(), 3, '0');
    private final String aesName = getConfig().getAesName();

    private LocalDate currentDay = LocalDate.now(ZoneId.of(getConfig().getZone()));
    private int seq = 1;


    @Getter private static final FilenameService instance = new FilenameService();

    private FilenameService() {
    }


    public String generate() {
        LocalDate today = LocalDate.now(ZoneId.of(getConfig().getZone()));

        if (!today.equals(currentDay)) {
            currentDay = today;
            seq = 1;
        }

        int N = seq++;

        int yulian = currentDay.getDayOfYear();

        return String.format("Z%s%s.%s_ENROLL%s%s%d.%03d",
                        bankCode, branchCode, aesName,
                        bankCode, branchCode,
                        N, yulian);
    }


}
