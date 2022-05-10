package com.asanpardakht.atmemulator;

public enum Messages {
    ERROR_500_OCCURRED("خطایی در سامانه رخ داد.");
    private final String value;

    Messages(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

