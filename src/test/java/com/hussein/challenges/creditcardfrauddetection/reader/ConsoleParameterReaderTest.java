package com.hussein.challenges.creditcardfrauddetection.reader;

import com.hussein.challenges.creditcardfrauddetection.config.UserParameters;
import com.hussein.challenges.creditcardfrauddetection.config.UserParameters.UserParametersBuilder;
import com.hussein.challenges.creditcardfrauddetection.reader.ConsoleDevice;
import com.hussein.challenges.creditcardfrauddetection.reader.ConsoleParameterReader;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ConsoleParameterReaderTest {

    private static final String FRAUD_DETECTION_DATE = "2017-12-08";
    private static final String FRAUD_THRESHOLD = "200";
    private ConsoleParameterReader consoleParameterReader;

    @Mock
    private ConsoleDevice console;

    @Before
    public void setUp() {
        initMocks(this);
        consoleParameterReader = new ConsoleParameterReader(console);
    }

    @Test
    public void shouldReturnUserParametersWhenParametersArePassedCorrectly() {
        when(console.readLine())
                .thenReturn(FRAUD_DETECTION_DATE)
                .thenReturn(FRAUD_THRESHOLD);

        UserParameters actual = consoleParameterReader.read();

        assertThat(actual).isEqualToComparingFieldByField(
                new UserParametersBuilder().setCheckDate(LocalDate.parse(FRAUD_DETECTION_DATE))
                        .setTotalThreshold(Double.valueOf(FRAUD_THRESHOLD)).build()
        );
    }

    @Test
    public void shouldRetryReadingDateWhenUserProvidedUnexpectedDateFormat() {
        when(console.readLine())
                .thenReturn("Unexpected DATE")
                .thenReturn(FRAUD_DETECTION_DATE)
                .thenReturn(FRAUD_THRESHOLD);

        UserParameters actual = consoleParameterReader.read();

        verify(console).write("Please insert the date to check the fraud in (it needs to be in the format yyyy-mm-dd): ");
        assertThat(actual).isEqualToComparingFieldByField(
                new UserParametersBuilder().setCheckDate(LocalDate.parse(FRAUD_DETECTION_DATE))
                        .setTotalThreshold(Double.valueOf(FRAUD_THRESHOLD)).build()
        );
    }

    @Test
    public void shouldRetryReadingThresholdWhenUserProvidedNotANumber() {
        when(console.readLine())
                .thenReturn(FRAUD_DETECTION_DATE)
                .thenReturn("Not a number")
                .thenReturn(FRAUD_THRESHOLD);

        UserParameters actual = consoleParameterReader.read();

        verify(console).write("Please insert the fraud detection threshold (it needs to be in the numbers with decimal points): ");
        assertThat(actual).isEqualToComparingFieldByField(
                new UserParametersBuilder().setCheckDate(LocalDate.parse(FRAUD_DETECTION_DATE))
                        .setTotalThreshold(Double.valueOf(FRAUD_THRESHOLD)).build()
        );
    }
}