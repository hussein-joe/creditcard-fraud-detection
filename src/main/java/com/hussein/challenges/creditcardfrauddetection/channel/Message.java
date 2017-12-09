package com.hussein.challenges.creditcardfrauddetection.channel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Message<T> {
    private final T payload;

    public Message(T payload) {
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
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
