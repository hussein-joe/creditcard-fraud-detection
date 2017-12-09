package com.hussein.challenges.creditcardfrauddetection.reader.file;

import com.google.common.base.Splitter;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.hussein.challenges.creditcardfrauddetection.channel.MessageChannel;
import com.hussein.challenges.creditcardfrauddetection.dto.TransactionRecordDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static com.google.common.io.Files.asCharSink;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class TransactionFileReaderAdapterTest {

    private TransactionFileReaderAdapter adapter;
    @Mock
    private FileRecordMapper<TransactionRecordDto> fileRecordMapper;
    @Mock
    private MessageChannel<TransactionRecordDto> outputChannel;
    private Path testFile;

    @Before
    public void setUp() {
        initMocks(this);

        adapter = new TransactionFileReaderAdapter(fileRecordMapper, outputChannel);
        testFile = Paths.get("testFile.txt");
    }

    @After
    public void cleanup() {
        testFile.toFile().delete();
    }

    @Test
    public void shouldUseMapperToMapFileLinesWhenFileHasLines() throws IOException {
        String transactionLine1 = "10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.00";
        String transactionLine2 = "10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T15:15:54, 20.00";

        givenTransactionFileWithLines(transactionLine1,
                transactionLine2);

        adapter.consumeFileLines(testFile);

        verify(fileRecordMapper).map(toFileRecordSource(transactionLine1));
        verify(fileRecordMapper).map(toFileRecordSource(transactionLine2));
    }

    private void givenTransactionFileWithLines(String... transactionLines) throws IOException {
        CharSink charSink = asCharSink(testFile.toFile(), Charset.defaultCharset(), FileWriteMode.APPEND);
        charSink.writeLines(Stream.of(transactionLines));
    }

    private FileRecordSource toFileRecordSource(String transactionRecord) {
        return new FileRecordSource(Splitter.on(",").trimResults().splitToList(transactionRecord).toArray(new String[]{}));
    }
}