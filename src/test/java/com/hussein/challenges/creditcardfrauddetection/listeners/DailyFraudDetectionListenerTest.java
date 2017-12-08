package com.hussein.challenges.creditcardfrauddetection.listeners;


import com.hussein.challenges.creditcardfrauddetection.UserParameters;
import com.hussein.challenges.creditcardfrauddetection.channel.Message;
import com.hussein.challenges.creditcardfrauddetection.dto.TransactionRecordDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Observable;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class DailyFraudDetectionListenerTest {

    private static final String CREDIT_CARD_HASH = "1234";
    private static final double TRANSACTION_TOTAL = 12.50;
    public static final LocalDateTime TRANSACTION_TIME = LocalDateTime.of(2017, Month.DECEMBER, 7, 19, 50);

    private DailyFraudDetectionListener listener;
    @Mock
    private Observable observable;
    @Mock
    private UserParameters userParameters;
    @Mock
    private FraudCreditCardsContainer fraudCreditCardsContainer;

    @Before
    public void setUp() {
        initMocks(this);
        listener = new DailyFraudDetectionListener(userParameters, fraudCreditCardsContainer);
    }

    @Test
    public void shouldDetectFraudWhenReceive2TransactionWithTotalGreaterThanTheThreshold() {
        when(userParameters.getTotalThreshold()).thenReturn(100d);
        when(userParameters.getCheckDate()).thenReturn(TRANSACTION_TIME.toLocalDate());
        TransactionRecordDto transaction1 = new TransactionRecordDto(CREDIT_CARD_HASH, TRANSACTION_TIME,
                50d);
        TransactionRecordDto transaction2 = new TransactionRecordDto(CREDIT_CARD_HASH, TRANSACTION_TIME,
                52d);

        listener.update(observable, new Message<>(transaction1));
        listener.update(observable, new Message<>(transaction2));

        verify(fraudCreditCardsContainer).addFraudCreditCard(CREDIT_CARD_HASH, 102);
    }

    @Test
    public void shouldIgnoreTransactionsWhenDateIsNotTheSameAsTheConfiguredFraudDetectionDate() {
        when(userParameters.getCheckDate()).thenReturn(LocalDate.parse("1984-05-01"));
        when(userParameters.getTotalThreshold()).thenReturn(100d);
        TransactionRecordDto transaction1 = new TransactionRecordDto(CREDIT_CARD_HASH, TRANSACTION_TIME,
                50d);
        TransactionRecordDto transaction2 = new TransactionRecordDto(CREDIT_CARD_HASH, TRANSACTION_TIME,
                52d);

        listener.update(observable, new Message<>(transaction1));
        listener.update(observable, new Message<>(transaction2));

        verify(fraudCreditCardsContainer, never()).addFraudCreditCard(CREDIT_CARD_HASH, 102);
    }
}