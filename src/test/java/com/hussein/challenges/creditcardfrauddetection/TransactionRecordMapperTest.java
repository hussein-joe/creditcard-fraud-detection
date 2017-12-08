package com.hussein.challenges.creditcardfrauddetection;

import com.hussein.challenges.creditcardfrauddetection.dto.TransactionRecordDto;
import com.hussein.challenges.creditcardfrauddetection.reader.file.FileRecordSource;
import com.hussein.challenges.creditcardfrauddetection.reader.file.TransactionRecordMapper;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionRecordMapperTest {

    private static final String CREDIT_CARD_HASH = "1234";
    private static final double TRANSACTION_TOTAL = 12.50;
    public static final LocalDateTime TRANSACTION_TIME = LocalDateTime.of(2017, Month.DECEMBER, 7, 19, 50);


    private TransactionRecordMapper recordMapper;

    @Before
    public void setUp() {

        recordMapper = new TransactionRecordMapper();
    }

    @Test
    public void shouldMapRecordFieldsToRecordDto() {
        FileRecordSource recordSource = givenTransactionFields(CREDIT_CARD_HASH,
                TRANSACTION_TIME.format(DateTimeFormatter.ISO_DATE_TIME), String.valueOf(TRANSACTION_TOTAL));

        TransactionRecordDto actual = recordMapper.map(recordSource, 0);

        assertThat(actual).isEqualToComparingFieldByField(new
                TransactionRecordDto(CREDIT_CARD_HASH, TRANSACTION_TIME, TRANSACTION_TOTAL));
    }

    @Test
    public void shouldThrowExceptionWhenTimeInUnexpectedFormat() {
        String transactionTime = "NOT A DATE";
        FileRecordSource recordSource = givenTransactionFields(CREDIT_CARD_HASH,
                transactionTime, String.valueOf(TRANSACTION_TOTAL));

        assertThatThrownBy(() -> recordMapper.map(recordSource, 0)).isInstanceOf(DateTimeParseException.class)
                .hasMessageContaining("Text '" + transactionTime + "' could not be parsed at index 0");
    }

    @Test
    public void shouldThrowExceptionWhenTotalIsNotNumber() {
        String transactionTotal = "NOT A NUMBER";
        FileRecordSource recordSource = givenTransactionFields(CREDIT_CARD_HASH,
                TRANSACTION_TIME.format(DateTimeFormatter.ISO_DATE_TIME), transactionTotal);

        assertThatThrownBy(() -> recordMapper.map(recordSource, 0)).isInstanceOf(NumberFormatException.class)
                .hasMessageContaining("For input string: \"" + transactionTotal + "\"");
    }

    private FileRecordSource givenTransactionFields(String creditCardHash, String timestamp, String amount) {
        FileRecordSource recordSource = mock(FileRecordSource.class);
        when(recordSource.get(0)).thenReturn(creditCardHash);
        when(recordSource.get(1)).thenReturn(timestamp);
        when(recordSource.get(2)).thenReturn(amount);
        return recordSource;
    }
}