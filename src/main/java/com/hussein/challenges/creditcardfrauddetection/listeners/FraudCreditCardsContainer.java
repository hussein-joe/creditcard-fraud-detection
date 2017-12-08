package com.hussein.challenges.creditcardfrauddetection.listeners;

import java.util.ArrayList;
import java.util.List;

public class FraudCreditCardsContainer {
    private final List<FraudCreditCard> fraudCreditCards;

    public FraudCreditCardsContainer() {
        fraudCreditCards = new ArrayList<>();
    }

    public void addFraudCreditCard(String creditCardHash, double totalAmount) {
        this.fraudCreditCards.add(new FraudCreditCard(creditCardHash, totalAmount));
    }

    public static class FraudCreditCard {
        private final String creditCardHash;
        private final double totalTransactionAmount;

        public FraudCreditCard(String creditCardHash, double totalTransactionAmount) {
            this.creditCardHash = creditCardHash;
            this.totalTransactionAmount = totalTransactionAmount;
        }

        public String getCreditCardHash() {
            return creditCardHash;
        }

        public double getTotalTransactionAmount() {
            return totalTransactionAmount;
        }


    }
}
