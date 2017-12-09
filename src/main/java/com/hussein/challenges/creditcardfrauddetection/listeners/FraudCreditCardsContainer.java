package com.hussein.challenges.creditcardfrauddetection.listeners;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Simple implementation of saving fraud credit card transactions in a list
 * This can be replaced by saving the fraud transactions in database or publish
 * events to another channel for example.
 */
public class FraudCreditCardsContainer {
    private final List<FraudCreditCard> fraudCreditCards;

    public FraudCreditCardsContainer() {
        fraudCreditCards = new ArrayList<>();
    }

    public void addFraudCreditCard(String creditCardHash, LocalDate fraudDate, double totalAmount) {
        this.fraudCreditCards.add(new FraudCreditCard(creditCardHash, fraudDate, totalAmount));
    }

    public void consumeFraudCreditCards(Consumer<FraudCreditCard> consumer) {
        fraudCreditCards.forEach(consumer);
    }

    /**
     * Return a copy of saved fraud credit cards.
     *
     * @return An immutable copy of the fraud credit cards to save the state of the this class from any
     * misuse or change outside the scope of this class.
     */
    public List<FraudCreditCard> getFraudCreditCards() {
        return ImmutableList.copyOf(fraudCreditCards);
    }

    public static class FraudCreditCard {
        private final String creditCardHash;
        private final double totalTransactionAmount;
        private final LocalDate fraudDate;

        FraudCreditCard(String creditCardHash, LocalDate fraudDate, double totalTransactionAmount) {
            this.creditCardHash = creditCardHash;
            this.fraudDate = fraudDate;
            this.totalTransactionAmount = totalTransactionAmount;
        }

        public String getCreditCardHash() {
            return creditCardHash;
        }

        public double getTotalTransactionAmount() {
            return totalTransactionAmount;
        }

        public LocalDate getFraudDate() {
            return fraudDate;
        }

        @Override
        public boolean equals(final Object other) {
            return EqualsBuilder.reflectionEquals(this, other);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }
    }
}
