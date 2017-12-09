package com.hussein.challenges.creditcardfrauddetection.reader;


/**
 * Just a wrapper around System console which is final class and can not be used in testing.
 */
public class ConsoleDevice {

    public String readLine() {
        return System.console().readLine();
    }

    public void write(String text, Object... args) {
        System.console().format(text, args);
    }
}
