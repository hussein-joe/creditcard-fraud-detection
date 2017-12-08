package com.hussein.challenges.creditcardfrauddetection;

import com.hussein.challenges.creditcardfrauddetection.dto.TransactionRecordDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class TransactionRecordsReaderImpl implements TransactionRecordsReader {
    private final Logger logger = LoggerFactory.getLogger(TransactionRecordsReaderImpl.class);
    @Override
    public List<TransactionRecordDto> read(InputStreamReader inputStreamReader) {

        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            while (true) {
                break;
            }
        } catch(IOException exp) {
            logger.error("Failed to read transactions from stream", exp);
            throw new RuntimeException(exp);
        }
        return null;
    }
}
