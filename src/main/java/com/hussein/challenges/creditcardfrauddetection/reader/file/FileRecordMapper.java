package com.hussein.challenges.creditcardfrauddetection.reader.file;

public interface FileRecordMapper<T> {

    T map(FileRecordSource recordSource, int rowNumber);
}
