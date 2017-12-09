package com.hussein.challenges.creditcardfrauddetection.listeners;

import com.hussein.challenges.creditcardfrauddetection.listeners.FraudCreditCardsContainer.FraudCreditCard;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class FraudCreditCardsContainerTest {
    private static final String CREDIT_CARD_HASH = "10d7ce2f43e35fa57d1bbf8b1e2";
    private static final double TRANSACTION_TOTAL = 10.00;
    private static final LocalDateTime TRANSACTION_TIME = LocalDateTime.parse("2014-04-29T13:15:54");

    private FraudCreditCardsContainer fraudCreditCardsContainer;

    @Before
    public void setUp() {
        initMocks(this);

        fraudCreditCardsContainer = new FraudCreditCardsContainer();
    }

    @Test
    public void shouldSaveAllFraudCreditCards() {
        fraudCreditCardsContainer.addFraudCreditCard(CREDIT_CARD_HASH,
                TRANSACTION_TIME.toLocalDate(), TRANSACTION_TOTAL);

        assertThat(fraudCreditCardsContainer.getFraudCreditCards()).containsOnly(
                new FraudCreditCard(CREDIT_CARD_HASH,
                        TRANSACTION_TIME.toLocalDate(), TRANSACTION_TOTAL)
        );
    }

    @Test
    public void shouldConsumeAllSavedFraudCreditCards() {
        fraudCreditCardsContainer.addFraudCreditCard(CREDIT_CARD_HASH,
                TRANSACTION_TIME.toLocalDate(), TRANSACTION_TOTAL);
        //Add another fraud credit card, same fields just for simplicity
        fraudCreditCardsContainer.addFraudCreditCard(CREDIT_CARD_HASH,
                TRANSACTION_TIME.toLocalDate(), TRANSACTION_TOTAL);

        fraudCreditCardsContainer.consumeFraudCreditCards(s-> assertThat(s).isEqualToComparingFieldByField(
                new FraudCreditCard(CREDIT_CARD_HASH,
                        TRANSACTION_TIME.toLocalDate(), TRANSACTION_TOTAL)
        ));
    }
}