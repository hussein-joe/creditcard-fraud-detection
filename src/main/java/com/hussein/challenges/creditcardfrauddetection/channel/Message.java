package com.hussein.challenges.creditcardfrauddetection.channel;

public class Message<T> {
    private final T payload;

    public Message(T payload) {
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }
}
