package com.hussein.challenges.creditcardfrauddetection.reader.file;

import com.google.common.base.Objects;

public class FileRecordSource {
    private final String[] recordFields;

    public FileRecordSource(String[] recordFields) {
        this.recordFields = recordFields;
    }

    public String get(int index) {
        return recordFields[index];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileRecordSource that = (FileRecordSource) o;
        return Objects.equal(recordFields, that.recordFields);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(recordFields);
    }
}
