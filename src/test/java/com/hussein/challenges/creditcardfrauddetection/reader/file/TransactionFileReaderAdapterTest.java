package com.hussein.challenges.creditcardfrauddetection.reader.file;

import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.hussein.challenges.creditcardfrauddetection.channel.Message;
import com.hussein.challenges.creditcardfrauddetection.channel.MessageChannel;
import com.hussein.challenges.creditcardfrauddetection.dto.TransactionRecordDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static com.google.common.io.Files.asCharSink;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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
    public void shouldDelegateToMapperToMapFileLinesWhenIsNotEmpty() throws IOException {
        String transactionLine1 = "10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.00";
        String transactionLine2 = "10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T15:15:54, 20.00";

        givenTransactionFileWithLines(transactionLine1,
                transactionLine2);

        adapter.consumeFileLines(testFile);

        verify(fileRecordMapper).map(transactionLine1);
        verify(fileRecordMapper).map(transactionLine2);
    }

    @Test
    public void shouldIgnoreEmptyLines() throws IOException {
        String transactionLine1 = "10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.00";
        String transactionLine2 = "10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T15:15:54, 20.00";

        givenTransactionFileWithLines(transactionLine1,
                "       ",
                "                         ",
                transactionLine2,
                "");

        adapter.consumeFileLines(testFile);

        verify(fileRecordMapper).map(transactionLine1);
        verify(fileRecordMapper).map(transactionLine2);
    }

    @Test
    public void shouldThrowExceptionWhenPassedFileDoesNotExist() throws IOException {
        assertThatThrownBy(() -> adapter.consumeFileLines(Paths.get("NO FILE EXISTS")))
                .isInstanceOf(NoSuchFileException.class)
                .hasMessageContaining("NO FILE EXISTS");
    }

    @Test
    public void shouldSendAllMappedTransactionRecordsToOutputChannel() throws IOException {
        String transactionLine1 = "10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.00";
        String transactionLine2 = "10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T15:15:54, 20.00";
        givenTransactionFileWithLines(transactionLine1,
                transactionLine2);
        TransactionRecordDto transactionRecordDto1 = mock(TransactionRecordDto.class);
        TransactionRecordDto transactionRecordDto2 = mock(TransactionRecordDto.class);
        when(fileRecordMapper.map(transactionLine1)).thenReturn(transactionRecordDto1);
        when(fileRecordMapper.map(transactionLine2)).thenReturn(transactionRecordDto2);

        adapter.consumeFileLines(testFile);

        verify(outputChannel).send(new Message<>(transactionRecordDto1));
        verify(outputChannel).send(new Message<>(transactionRecordDto2));
    }

    private void givenTransactionFileWithLines(String... transactionLines) throws IOException {
        CharSink charSink = asCharSink(testFile.toFile(), Charset.defaultCharset(), FileWriteMode.APPEND);
        charSink.writeLines(Stream.of(transactionLines));
    }
}