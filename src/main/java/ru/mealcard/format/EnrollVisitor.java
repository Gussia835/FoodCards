package ru.mealcard.format;

import org.apache.commons.lang3.StringUtils;
import ru.mealcard.dto.BodyDTO;
import ru.mealcard.dto.HeaderDTO;
import ru.mealcard.dto.TrailerDTO;
import ru.mealcard.models.TypeProcedure;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EnrollVisitor implements Visitor {
    private final DateTimeFormatter YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final DateTimeFormatter HHMMSS = DateTimeFormatter.ofPattern("HHmmss");

    private static final int HEADER_LEN = 42;
    private static final int FIO_LEN = 100;
    private static final int ACCOUNT_LEN = 30;
    private static final int SUMM_LEN = 20;
    private static final int TYPE_LEN = 2;

    @Override
    public String visit(HeaderDTO header) {
        StringBuilder sb = new StringBuilder(HEADER_LEN);
        sb.append("H ")
                .append(header.getSendAt().format(YYYYMMDD))
                .append(" ")
                .append(header.getSendAt().format(HHMMSS))
                .append(" ")
                .append(StringUtils.rightPad(header.getProcType().getCode(), 9));

        if (header.getProcType() == TypeProcedure.IN_TIME &&
                header.getSheduledTime() != null) {
            sb.append(header.getSheduledTime().format(YYYYMMDD))
                    .append(" ")
                    .append(header.getSheduledTime().format(HHMMSS));
        } else {
            sb.append(StringUtils.repeat(" ", 8))
                    .append(" ")
                    .append(StringUtils.rightPad(" ", 6));
        }

        return StringUtils.rightPad(sb.toString(), HEADER_LEN);
    }

    @Override
    public String visit(BodyDTO body) {
        String fio = StringUtils.rightPad(StringUtils.truncate(
                StringUtils.defaultString(body.getFio()), FIO_LEN), FIO_LEN);
        String account = StringUtils.rightPad(
                StringUtils.truncate(StringUtils.defaultString(body.getAccount()), ACCOUNT_LEN), ACCOUNT_LEN);
        String type = StringUtils.rightPad(StringUtils.truncate(
                StringUtils.defaultString(body.getType().getCode()), TYPE_LEN), TYPE_LEN);
        String summ = StringUtils.leftPad(
                StringUtils.defaultString(String.valueOf(body.getSumm())), SUMM_LEN);


        return fio + account + type + summ;
    }

    @Override
    public String visit(TrailerDTO trailer) {
        return StringUtils.join("T",
                                StringUtils.repeat(' ', 9),
                                StringUtils.leftPad(String.valueOf(trailer.getCount()), 10));
    }
}
