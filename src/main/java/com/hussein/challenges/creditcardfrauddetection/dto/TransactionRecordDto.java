package com.hussein.challenges.creditcardfrauddetection.dto;

import java.time.LocalDateTime;

public class TransactionRecordDto {
    private final String creditCardHash;
    private final LocalDateTime time;
    private final double transactionTotal;

    public TransactionRecordDto(String creditCardHash, LocalDateTime time, double transactionTotal) {

        this.creditCardHash = creditCardHash;
        this.time = time;
        this.transactionTotal = transactionTotal;
    }

    public String getCreditCardHash() {
        return creditCardHash;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public double getTransactionTotal() {
        return transactionTotal;
    }
}
