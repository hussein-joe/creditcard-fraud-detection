package com.hussein.challenges.creditcardfrauddetection.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public boolean equals(final Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
