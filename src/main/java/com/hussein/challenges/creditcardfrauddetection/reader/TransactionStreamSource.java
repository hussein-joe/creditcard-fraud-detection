package com.hussein.challenges.creditcardfrauddetection.reader;

import com.hussein.challenges.creditcardfrauddetection.dto.TransactionRecordDto;

public interface TransactionStreamSource {
    void onSend(TransactionRecordDto transactionRecordDto);

    /**
     * Close this stream source as a result of reaching to the end of the stream or interrupting it.
     */
    void close();
}
