package com.hussein.challenges.creditcardfrauddetection;

import com.hussein.challenges.creditcardfrauddetection.channel.MessageChannel;
import com.hussein.challenges.creditcardfrauddetection.config.UserParameters;
import com.hussein.challenges.creditcardfrauddetection.dto.TransactionRecordDto;
import com.hussein.challenges.creditcardfrauddetection.listeners.DailyFraudDetectionListener;
import com.hussein.challenges.creditcardfrauddetection.listeners.FraudCreditCardsContainer;
import com.hussein.challenges.creditcardfrauddetection.reader.ConsoleDevice;
import com.hussein.challenges.creditcardfrauddetection.reader.ConsoleParameterReader;
import com.hussein.challenges.creditcardfrauddetection.reader.file.FileReaderAdapter;
import com.hussein.challenges.creditcardfrauddetection.reader.file.TransactionFileReaderAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class CreditCardFraudDetectionCommandLineRunner implements CommandLineRunner {
    @Autowired
    private ConsoleParameterReader consoleParameterReader;
    @Autowired
    private FileReaderAdapter transactionFileReaderAdapter;
    @Autowired
    private FraudCreditCardsContainer fraudCreditCardsContainer;
    @Autowired
    private MessageChannel<TransactionRecordDto> transactionsChannel;
    @Autowired
    private ConsoleDevice consoleDevice;

    @Value("${application.config.transaction-file-path}")
    private String transactionFilePath;


    @Override
    public void run(String... strings) throws Exception {
        Path transactionFile = transactionFile();
        UserParameters userParameters = consoleParameterReader.read();
        configureFraudDetectionChannelListeners(userParameters);

        transactionFileReaderAdapter.consumeFileLines(transactionFile);
        fraudCreditCardsContainer.consumeFraudCreditCards(s->
                consoleDevice.writeLine("Credit card with hash %s is fraud with total amount %s in %s",
                    s.getCreditCardHash(), s.getTotalTransactionAmount(),
                        s.getFraudDate()));
    }

    private void configureFraudDetectionChannelListeners(UserParameters userParameters) {
        transactionsChannel.addObserver(new DailyFraudDetectionListener(userParameters, fraudCreditCardsContainer));
    }

    private Path transactionFile() {
        Path transactionFile = Paths.get(transactionFilePath);
        if (!transactionFile.toFile().exists()) {
            throw new RuntimeException("The configured transaction file does not exist");
        }
        return transactionFile;
    }
}
