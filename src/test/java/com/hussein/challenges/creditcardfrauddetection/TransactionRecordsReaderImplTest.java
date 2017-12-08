package com.hussein.challenges.creditcardfrauddetection;


import org.junit.Before;
import org.junit.Test;

import java.io.InputStreamReader;

import static org.mockito.Mockito.mock;

public class TransactionRecordsReaderImplTest {

    private TransactionRecordsReader reader;

    @Before
    public void setUp() throws Exception {
        reader = new TransactionRecordsReaderImpl();
    }

    @Test
    public void shouldReadAllTransactionRecordsUntilEnd() {
        InputStreamReader inputStream = mock(InputStreamReader.class);

        reader.read(inputStream);
    }
}