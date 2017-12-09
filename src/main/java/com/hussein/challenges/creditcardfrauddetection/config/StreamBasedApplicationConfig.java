package com.hussein.challenges.creditcardfrauddetection.config;

import com.hussein.challenges.creditcardfrauddetection.channel.DirectTransactionMessageChannel;
import com.hussein.challenges.creditcardfrauddetection.channel.MessageChannel;
import com.hussein.challenges.creditcardfrauddetection.dto.TransactionRecordDto;
import com.hussein.challenges.creditcardfrauddetection.listeners.FraudCreditCardsContainer;
import com.hussein.challenges.creditcardfrauddetection.reader.ConsoleDevice;
import com.hussein.challenges.creditcardfrauddetection.reader.ConsoleParameterReader;
import com.hussein.challenges.creditcardfrauddetection.reader.file.TransactionFileReaderAdapter;
import com.hussein.challenges.creditcardfrauddetection.reader.file.TransactionRecordMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Some of the bean are manually created. Some others are component which spring creates as part of component scan
 */
@Configuration
public class StreamBasedApplicationConfig {

    @Bean
    public TransactionFileReaderAdapter transactionFileReaderAdapter(MessageChannel<TransactionRecordDto> outputChannel) {
        return new TransactionFileReaderAdapter(new TransactionRecordMapper(),
                outputChannel);
    }

    @Bean
    public MessageChannel<TransactionRecordDto> transactionsChannel() {
        return new DirectTransactionMessageChannel();
    }

    @Bean
    public FraudCreditCardsContainer fraudCreditCardsContainer() {
        return new FraudCreditCardsContainer();
    }

    @Bean
    public ConsoleParameterReader consoleParameterReader(ConsoleDevice consoleDevice) {
        return new ConsoleParameterReader(consoleDevice);
    }
}
