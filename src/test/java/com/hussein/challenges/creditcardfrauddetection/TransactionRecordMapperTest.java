package com.hussein.challenges.creditcardfrauddetection;

import com.google.common.base.Joiner;
import com.hussein.challenges.creditcardfrauddetection.dto.TransactionRecordDto;
import com.hussein.challenges.creditcardfrauddetection.reader.file.TransactionRecordMapper;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

public class TransactionRecordMapperTest {
    private static final String CREDIT_CARD_HASH = "10d7ce2f43e35fa57d1bbf8b1e2";
    private static final Double TRANSACTION_TOTAL = 12.50;
    private static final String TRANSACTION_TIME = "2014-04-29T13:15:54";

    private TransactionRecordMapper recordMapper;

    @Before
    public void setUp() {
        recordMapper = new TransactionRecordMapper();
    }

    @Test
    public void shouldMapRecordToTransactionDto() {
        String record = givenTransactionFieldsGenerateRecord(CREDIT_CARD_HASH,
                TRANSACTION_TIME, TRANSACTION_TOTAL.toString());

        TransactionRecordDto actual = recordMapper.map(record);

        assertThat(actual).isEqualToComparingFieldByField(new
                TransactionRecordDto(CREDIT_CARD_HASH, LocalDateTime.parse(TRANSACTION_TIME), TRANSACTION_TOTAL));
    }

    @Test
    public void shouldThrowExceptionWhenNumberOfRecordFieldsAreNot3() {
        String record = CREDIT_CARD_HASH + ", " + TRANSACTION_TIME;

        assertThatThrownBy(() -> recordMapper.map(record))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Unexpected transaction record " + record);
    }

    @Test
    public void shouldThrowExceptionWhenTransactionTimeInUnexpectedFormat() {
        String transactionTime = "NOT A DATE";
        String record = givenTransactionFieldsGenerateRecord(CREDIT_CARD_HASH,
                transactionTime, TRANSACTION_TOTAL.toString());

        assertThatThrownBy(() -> recordMapper.map(record))
                .isInstanceOf(DateTimeParseException.class)
                .hasMessageContaining("Text '" + transactionTime + "' could not be parsed at index 0");
    }

    @Test
    public void shouldThrowExceptionWhenTotalIsNotANumber() {
        String transactionTotal = "NOT A NUMBER";
        String record = givenTransactionFieldsGenerateRecord(CREDIT_CARD_HASH,
                TRANSACTION_TIME, transactionTotal);

        assertThatThrownBy(() -> recordMapper.map(record))
                .isInstanceOf(NumberFormatException.class)
                .hasMessageContaining("For input string: \"" + transactionTotal + "\"");
    }

    private String givenTransactionFieldsGenerateRecord(String creditCardHash, String transactionTime, String transactionTotal) {
        return Joiner.on(", ").join(creditCardHash
                , transactionTime, transactionTotal);
    }
}