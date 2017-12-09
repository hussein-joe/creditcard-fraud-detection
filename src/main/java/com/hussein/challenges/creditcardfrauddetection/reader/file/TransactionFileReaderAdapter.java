package com.hussein.challenges.creditcardfrauddetection.reader.file;

import com.hussein.challenges.creditcardfrauddetection.channel.Message;
import com.hussein.challenges.creditcardfrauddetection.channel.MessageChannel;
import com.hussein.challenges.creditcardfrauddetection.dto.TransactionRecordDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * An adapter which reads the lines from file and send to a message channel
 */
public class TransactionFileReaderAdapter {
    private final FileRecordMapper<TransactionRecordDto> fileRecordMapper;
    private final MessageChannel<TransactionRecordDto> outputChannel;

    public TransactionFileReaderAdapter(FileRecordMapper<TransactionRecordDto> fileRecordMapper, MessageChannel<TransactionRecordDto> outputChannel) {
        this.fileRecordMapper = fileRecordMapper;
        this.outputChannel = outputChannel;
    }

    public void consumeFileLines(Path path) throws IOException {
        Stream<String> fileLines = Files.lines(path);
        Consumer<String> transactionConsumer = s -> {
            TransactionRecordDto transactionRecordDto = fileRecordMapper.map(s);
            outputChannel.send(new Message<>(transactionRecordDto));
        };

        fileLines.map(String::trim)
                .filter(s -> ! s.isEmpty())
                .forEach(transactionConsumer);
    }
}
