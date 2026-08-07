package ru.mealcard.service;

import lombok.Getter;
import ru.mealcard.Base;

import net.datafaker.Faker;
import ru.mealcard.dto.CardDTO;
import ru.mealcard.dto.RequestDTO;

import java.util.ArrayList;
import java.util.List;

public class MockDataService extends Base {
    @Getter private static final MockDataService instance = new MockDataService();

    private MockDataService() {
    }

    private final Faker faker = new Faker();

    public RequestDTO generateRequest(int count) {
        List<CardDTO> cards = new ArrayList<>();

        for (int i = 0; i < count; ++i) {
            cards.add(getCardRandom());
        }

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setCards(cards);
        info("generated {} cards", count);

        return requestDTO;
    }

    private CardDTO getCardRandom() {
        CardDTO card = new CardDTO();

        card.setFio(faker.name().fullName());
        card.setAccount(faker.numerify("1###############"));
        card.setType(faker.options().option("DR", "CR", "ZR"));
        card.setSum(faker.number().numberBetween(1, 100_000_000));

        debug("card was generated: {}", card);

        return card;
    }
}
