package com.hussein.challenges.creditcardfrauddetection.reader.file;

import com.hussein.challenges.creditcardfrauddetection.dto.TransactionRecordDto;

import java.time.LocalDateTime;

public class TransactionRecordMapper implements FileRecordMapper<TransactionRecordDto> {
    private static final int CREDIT_HASH_INDEX = 0;
    private static final int TRANSACTION_TIME_INDEX = 1;
    private static final int TRANSACTION_TOTAL_INDEX = 2;

    @Override
    public TransactionRecordDto map(FileRecordSource recordSource) {
        return new TransactionRecordDto(recordSource.get(CREDIT_HASH_INDEX),
                formatTransactionTime(recordSource.get(TRANSACTION_TIME_INDEX)),
                Double.valueOf(recordSource.get(TRANSACTION_TOTAL_INDEX)));
    }

    private LocalDateTime formatTransactionTime(String time) {
        return LocalDateTime.parse(time);
    }
}
