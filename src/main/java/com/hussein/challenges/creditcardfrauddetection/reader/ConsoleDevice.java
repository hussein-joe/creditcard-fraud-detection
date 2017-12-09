package com.hussein.challenges.creditcardfrauddetection.reader;


import org.springframework.stereotype.Component;

/**
 * Just a wrapper around System console which is final class and can not be used in testing.
 */
@Component
public class ConsoleDevice {

    public String readLine() {
        return System.console().readLine();
    }

    public void write(String text, Object... args) {
        System.console().format(text, args);
    }

    public void writeLine(String text, Object... args) {
        write(text, args);
        write(System.lineSeparator());
    }
}
