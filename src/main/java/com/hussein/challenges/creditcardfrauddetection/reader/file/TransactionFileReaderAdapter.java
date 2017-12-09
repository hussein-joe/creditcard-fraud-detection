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
 * This is an example of Template design pattern.
 */
public class TransactionFileReaderAdapter extends AbstractFileLinesReaderAdapter<TransactionRecordDto> {
    private final MessageChannel<TransactionRecordDto> outputChannel;

    public TransactionFileReaderAdapter(FileRecordMapper<TransactionRecordDto> fileRecordMapper,
                                        MessageChannel<TransactionRecordDto> outputChannel) {
        super(fileRecordMapper);
        this.outputChannel = outputChannel;
    }

    @Override
    protected Consumer<TransactionRecordDto> consumeLine() {
        return s -> outputChannel.send( new Message<>(s) );
    }
}
