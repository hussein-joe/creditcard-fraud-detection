package com.hussein.challenges.creditcardfrauddetection.reader;

import com.hussein.challenges.creditcardfrauddetection.channel.MessageChannel;
import com.hussein.challenges.creditcardfrauddetection.dto.TransactionRecordDto;

public class ConsoleTransactionStreamSource implements TransactionStreamSource {
    /*private final ConsoleDevice console;
    private MessageChannel<TransactionRecordDto> messageChannel;
*/
    @Override
    public void onSend(TransactionRecordDto transactionRecordDto) {

    }

    @Override
    public void close() {

    }
}
