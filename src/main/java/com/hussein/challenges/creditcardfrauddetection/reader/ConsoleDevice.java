package com.hussein.challenges.creditcardfrauddetection.reader;


public class ConsoleDevice {

    public String readLine() {
        return System.console().readLine();
    }

    public void write(String text, Object... args) {
        System.console().format(text, args);
    }
}
