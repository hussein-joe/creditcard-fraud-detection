package com.hussein.challenges.creditcardfrauddetection.channel;

import com.hussein.challenges.creditcardfrauddetection.dto.TransactionRecordDto;

/**
 * Synchronous implementation of the message channel used as a transport to dispatch payloads of type TransactionRecordDto
 */

public class DirectTransactionMessageChannel extends MessageChannel<TransactionRecordDto> {
    @Override
    public void send(Message<TransactionRecordDto> message) {
        super.setChanged();
        super.notifyObservers(message);
    }
}
