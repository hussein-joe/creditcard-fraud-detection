package com.hussein.challenges.creditcardfrauddetection;

import java.time.LocalDate;

public class UserParameters {
    private final LocalDate checkDate;
    private final double totalThreshold;

    UserParameters(LocalDate checkDate, double totalThreshold) {
        this.checkDate = checkDate;
        this.totalThreshold = totalThreshold;
    }

    public LocalDate getCheckDate() {
        return checkDate;
    }

    public double getTotalThreshold() {
        return totalThreshold;
    }

    public static class UserParametersBuilder {
        private LocalDate checkDate;
        private double totalThreshold;

        public UserParametersBuilder setCheckDate(LocalDate checkDate) {
            this.checkDate = checkDate;
            return this;
        }

        public UserParametersBuilder setTotalThreshold(double totalThreshold) {
            this.totalThreshold = totalThreshold;
            return this;
        }

        public UserParameters build() {
            return new UserParameters(checkDate, totalThreshold);
        }
    }
}
