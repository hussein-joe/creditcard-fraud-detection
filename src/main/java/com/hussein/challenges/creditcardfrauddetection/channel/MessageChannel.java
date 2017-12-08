package com.hussein.challenges.creditcardfrauddetection.channel;

import java.util.Observable;

/**
 * The channel used to send messages from the source to one or more listeners
 * I used standard Java implementation of observer pattern just for simplicity.
 * All implementations of this message channel are of type subscribable, it does not support polling.
 * @param <T>
 */
public abstract class MessageChannel<T> extends Observable {
    public abstract void send(Message<T> message);
}
