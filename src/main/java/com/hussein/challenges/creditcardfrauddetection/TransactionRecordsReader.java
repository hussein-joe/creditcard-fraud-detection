package com.hussein.challenges.creditcardfrauddetection;

import com.hussein.challenges.creditcardfrauddetection.dto.TransactionRecordDto;

import java.io.InputStreamReader;
import java.util.List;

public interface TransactionRecordsReader {

    List<TransactionRecordDto> read(InputStreamReader inputStreamReader);
    default boolean endOfTransactions(String record) {
        return record.equalsIgnoreCase("end");
    }
}
