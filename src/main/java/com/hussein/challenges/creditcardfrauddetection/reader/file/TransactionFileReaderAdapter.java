package com.hussein.challenges.creditcardfrauddetection.reader.file;

import com.google.common.base.Splitter;
import com.hussein.challenges.creditcardfrauddetection.channel.MessageChannel;
import com.hussein.challenges.creditcardfrauddetection.dto.TransactionRecordDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class TransactionFileReaderAdapter {
    private final FileRecordMapper<TransactionRecordDto> fileRecordMapper;
    private final MessageChannel<TransactionRecordDto> outputChannel;


    public TransactionFileReaderAdapter(FileRecordMapper<TransactionRecordDto> fileRecordMapper, MessageChannel<TransactionRecordDto> outputChannel) {
        this.fileRecordMapper = fileRecordMapper;
        this.outputChannel = outputChannel;
    }

    public void consumeFileLines(Path path) {
        try {
            Stream<String> fileLines = Files.lines(path);
            fileLines.filter(s->!s.isEmpty()).map(String::trim).forEach(s -> {
                TransactionRecordDto transactionRecordDto = fileRecordMapper.map(fileRecordSource(s));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FileRecordSource fileRecordSource(String fileLine) {
        return new FileRecordSource(transactionLineFields(fileLine));
    }

    private String[] transactionLineFields(String fileLine) {
        return Splitter.on(",").trimResults().splitToList(fileLine).toArray(new String[]{});
    }
}
