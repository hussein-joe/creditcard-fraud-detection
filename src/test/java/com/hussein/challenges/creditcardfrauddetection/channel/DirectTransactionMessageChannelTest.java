package com.hussein.challenges.creditcardfrauddetection.channel;

import com.hussein.challenges.creditcardfrauddetection.dto.TransactionRecordDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Observer;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * There are more cases to test like the order of notifying observers, I did not write test for them because they should be
 * covered already using Java standard Observable.
 * AND also, it is not a production code
 */
public class DirectTransactionMessageChannelTest {

    private static final String CREDIT_CARD_HASH = "1234";
    private static final double TRANSACTION_TOTAL = 12.50;
    public static final LocalDateTime TRANSACTION_TIME = LocalDateTime.of(2017, Month.DECEMBER, 7, 19, 50);

    private DirectTransactionMessageChannel directTransactionMessageChannel;
    @Mock
    private Observer observer1;
    @Mock
    private Observer observer2;

    @Before
    public void setUp() {
        initMocks(this);
        directTransactionMessageChannel = new DirectTransactionMessageChannel();
    }

    @Test
    public void givenOneObserverIsRegisteredShouldNotifyRegisteredObserverWhenNewMessageSentToChannel() {
        directTransactionMessageChannel.addObserver(observer1);
        TransactionRecordDto transactionDto = new TransactionRecordDto(CREDIT_CARD_HASH, TRANSACTION_TIME,
                TRANSACTION_TOTAL);
        Message<TransactionRecordDto> message = new Message<>(transactionDto);

        directTransactionMessageChannel.send(message);

        verify(observer1).update(directTransactionMessageChannel, message);
    }

    @Test
    public void shouldNotifyAllRegisteredObserversWhenSendNewMessageToChannel() {
        directTransactionMessageChannel.addObserver(observer1);
        directTransactionMessageChannel.addObserver(observer2);
        TransactionRecordDto transactionDto = new TransactionRecordDto(CREDIT_CARD_HASH, TRANSACTION_TIME,
                TRANSACTION_TOTAL);
        Message<TransactionRecordDto> message = new Message<>(transactionDto);

        directTransactionMessageChannel.send(message);

        verify(observer1).update(directTransactionMessageChannel, message);
        verify(observer2).update(directTransactionMessageChannel, message);
    }
}