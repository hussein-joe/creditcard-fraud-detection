package com.hussein.challenges.creditcardfrauddetection.reader.file;

import com.google.common.base.Splitter;
import com.hussein.challenges.creditcardfrauddetection.dto.TransactionRecordDto;

import java.time.LocalDateTime;
import java.util.List;

import static java.lang.String.format;

public class TransactionRecordMapper implements FileRecordMapper<TransactionRecordDto> {
    private static final int CREDIT_HASH_INDEX = 0;
    private static final int TRANSACTION_TIME_INDEX = 1;
    private static final int TRANSACTION_TOTAL_INDEX = 2;
    private static final String SEPARATOR = ",";

    @Override
    public TransactionRecordDto map(String transactionRecord) {
        List<String> transactionFields = Splitter.on(SEPARATOR).trimResults().splitToList(transactionRecord);
        if ( transactionFields.size() != 3 ) {
            throw new RuntimeException(format("Unexpected transaction record %s", transactionRecord));
        }

        return new TransactionRecordDto(transactionFields.get(CREDIT_HASH_INDEX),
                formatTransactionTime(transactionFields.get(TRANSACTION_TIME_INDEX)),
                Double.valueOf(transactionFields.get(TRANSACTION_TOTAL_INDEX)));
    }

    private LocalDateTime formatTransactionTime(String time) {
        return LocalDateTime.parse(time);
    }
}
