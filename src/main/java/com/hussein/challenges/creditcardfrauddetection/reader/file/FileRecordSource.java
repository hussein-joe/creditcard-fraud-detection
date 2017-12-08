package com.hussein.challenges.creditcardfrauddetection.reader.file;

public class FileRecordSource {
    private final String[] recordFields;

    public FileRecordSource(String[] recordFields) {
        this.recordFields = recordFields;
    }

    public String get(int index) {
        return recordFields[index];
    }
}
