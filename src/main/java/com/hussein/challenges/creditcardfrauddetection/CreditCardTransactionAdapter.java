package com.hussein.challenges.creditcardfrauddetection;

import com.hussein.challenges.creditcardfrauddetection.dto.TransactionRecordDto;
import com.hussein.challenges.creditcardfrauddetection.reader.file.TransactionRecordMapper;

import java.io.InputStreamReader;
import java.util.List;

public class CreditCardTransactionAdapter {
    private final TransactionRecordsReader recordsReader;
    private final TransactionRecordMapper recordMapper;

    public CreditCardTransactionAdapter(TransactionRecordsReader recordsReader, TransactionRecordMapper recordMapper) {
        this.recordsReader = recordsReader;
        this.recordMapper = recordMapper;
    }

    public List<TransactionRecordDto> read(InputStreamReader inputStreamReader) {
        recordsReader.read(inputStreamReader);
        return null;
    }
}
