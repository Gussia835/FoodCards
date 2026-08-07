package ru.mealcard.service;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import ru.mealcard.Base;
import ru.mealcard.dto.BodyDTO;
import ru.mealcard.dto.CardDTO;
import ru.mealcard.dto.HeaderDTO;
import ru.mealcard.dto.RequestDTO;
import ru.mealcard.models.FileContent;
import ru.mealcard.models.TypeOperation;
import ru.mealcard.models.TypeProcedure;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class RequestConverterService extends Base {
    @Getter
    private static final RequestConverterService instance = new RequestConverterService();

    private RequestConverterService() {
    }

    private HeaderDTO toHeader(CardDTO card) {
        TypeProcedure typeProc = TypeProcedure.fromCode(card.getProcType().toLowerCase());
        LocalDateTime sheduledDateTime = card.getSheduledDateTime();

        if (typeProc == TypeProcedure.IN_TIME && card.getSheduledDateTime() == null) {
            typeProc = TypeProcedure.IMMEDIATE;
            sheduledDateTime = LocalDateTime.now();
        }

        HeaderDTO header = new HeaderDTO(sheduledDateTime, typeProc,
                                    LocalDateTime.now(ZoneId.of(getConfig().getZone())));

        return header;
    }

    private BodyDTO toBody(CardDTO card) {
        if (StringUtils.isBlank(card.getAccount())) {
            error("account of card is empty", card, card.getAccount());
            throw new IllegalArgumentException("account cant be empty");
        }

        if (!card.getAccount().trim().matches("\\d{16}")) {
            error("account not 16-digits", card, card.getAccount());
            throw new IllegalArgumentException("account must consist of 16-digits");
        }

        if (StringUtils.isBlank(card.getType())) {
            error("type of card is empty", card, card.getType());
            throw new IllegalArgumentException("type in card cant be empty");
        }

        if (card.getSum() < 0) {
            error("summ of card less than 0", card, card.getSum());
            throw new IllegalArgumentException("summ of card cant be less than 0");
        }

        BodyDTO body = new BodyDTO(card.getFio(),
                        card.getAccount(),
                        TypeOperation.fromCode(card.getType()),
                        card.getSum());

        return body;
    }

    public FileContent convert(RequestDTO requestDTO) {
        List<CardDTO> cards = requestDTO.getCards();

        if (cards == null || cards.isEmpty()) {
            error("cards in request is empty. Cards: {}", cards);
            throw new IllegalArgumentException("cards cant be empty");
        }

        List<BodyDTO> bodies = cards.stream().map(this::toBody).toList();
        HeaderDTO header = toHeader(cards.getFirst());

        FileContent content = new FileContent(header, bodies);

        return content;
    }

}
