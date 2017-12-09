package com.hussein.challenges.creditcardfrauddetection.listeners;

import com.hussein.challenges.creditcardfrauddetection.config.UserParameters;
import com.hussein.challenges.creditcardfrauddetection.channel.Message;
import com.hussein.challenges.creditcardfrauddetection.dto.TransactionRecordDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Message channel listener
 */
public class DailyFraudDetectionListener implements Observer {
    private Logger logger = LoggerFactory.getLogger(DailyFraudDetectionListener.class);

    private final UserParameters userParameters;
    private final Map<String, Double> accumulatedTransactions;
    private final FraudCreditCardsContainer fraudCreditCardsContainer;

    public DailyFraudDetectionListener(UserParameters userParameters, FraudCreditCardsContainer fraudCreditCardsContainer) {
        this.userParameters = userParameters;
        this.accumulatedTransactions = new HashMap<>();
        this.fraudCreditCardsContainer = fraudCreditCardsContainer;
    }

    @Override
    public void update(Observable o, Object arg) {
        TransactionRecordDto transaction = getTransactionDto(arg);
        if ( ignoreTransaction(transaction) ) {
            logger.debug("The transaction for credit card of hash %s is ignored", transaction.getCreditCardHash());
            return;
        }

        Double totalAmount = updateAccumulatedTransactionAmountForCreditCard(transaction);
        if (totalAmount >= userParameters.getTotalThreshold()) {
            fraudCreditCardsContainer.addFraudCreditCard(transaction.getCreditCardHash(),
                    transaction.getTime().toLocalDate(), totalAmount);
        }
    }

    private boolean ignoreTransaction(TransactionRecordDto transaction) {
        return !transaction.getTime().toLocalDate().isEqual(userParameters.getCheckDate());
    }

    private Double updateAccumulatedTransactionAmountForCreditCard(TransactionRecordDto transaction) {
        Double creditCardTotalTransactionAmount = accumulatedTransactions.get(transaction.getCreditCardHash());
        if ( creditCardTotalTransactionAmount == null ) {
            creditCardTotalTransactionAmount = 0d;
        }
        creditCardTotalTransactionAmount += transaction.getTransactionTotal();
        accumulatedTransactions.put(transaction.getCreditCardHash(), creditCardTotalTransactionAmount);
        return creditCardTotalTransactionAmount;
    }

    private TransactionRecordDto getTransactionDto(Object arg) {
        return ((Message<TransactionRecordDto>) arg).getPayload();
    }
}
