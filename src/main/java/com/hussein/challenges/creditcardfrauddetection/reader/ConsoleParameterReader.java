package com.hussein.challenges.creditcardfrauddetection.reader;

import com.hussein.challenges.creditcardfrauddetection.UserParameters;

import java.time.LocalDate;

public class ConsoleParameterReader {

    private final ConsoleDevice console;

    public ConsoleParameterReader(ConsoleDevice console) {
        this.console = console;
    }

    public UserParameters read() {
        UserParameters.UserParametersBuilder userParametersBuilder = new UserParameters.UserParametersBuilder();
        userParametersBuilder.setCheckDate(readDate());
        userParametersBuilder.setTotalThreshold(readThreshold());

        return userParametersBuilder.build();
    }

    private LocalDate readDate() {
        console.write("Please insert the date to check the fraud in (it needs to be in the format yyyy-mm-dd): ");
        while(true) {

            try {
                String line = console.readLine().trim();
                return LocalDate.parse(line);
            } catch (Exception exp) {
                console.write("Sorry, please use the correct format to insert the date: ");
            }
        }
    }

    private double readThreshold() {
        console.write("Please insert the fraud detection threshold (it needs to be in the numbers with decimal points): ");
        while(true) {

            try {
                String line = console.readLine().trim();
                return Double.valueOf(line);
            } catch (Exception exp) {
                console.write("Sorry, please use the correct format to insert the total: ");
            }
        }
    }
}
