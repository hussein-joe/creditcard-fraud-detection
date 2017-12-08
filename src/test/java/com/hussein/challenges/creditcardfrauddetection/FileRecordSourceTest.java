package com.hussein.challenges.creditcardfrauddetection;

import com.hussein.challenges.creditcardfrauddetection.reader.file.FileRecordSource;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class FileRecordSourceTest {

    private FileRecordSource fileRecordSource;
    private String[] recordFields = new String[]{"1", "2", "3"};

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        fileRecordSource = new FileRecordSource(recordFields);
    }

    @Test
    public void shouldReturnField1InTheArrayWhenAskForElementAtIndex1() {
        String actual = fileRecordSource.get(1);

        assertThat(actual).isEqualTo(recordFields[1]);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenIndexDoesnotExist() {
        fileRecordSource.get(recordFields.length+1000);
    }
}