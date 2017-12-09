package com.hussein.challenges.creditcardfrauddetection.reader.file;

public interface FileRecordMapper<T> {

    T map(String transactionRecord);
}
