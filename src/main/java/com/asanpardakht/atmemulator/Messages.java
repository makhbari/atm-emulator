package com.asanpardakht.atmemulator;

public enum Messages {
    ERROR_500_OCCURRED("خطای داخلی سرویس"),
    CASH_DEPOSIT_COMPLETED_SUCCESSFULY("واریز پول با موفقیت انجام شده است."),
    INVALID_CARD_TYPE("نوع کارت نامعتبر است."),
    INVALID_CARD_STATUS("وضعیت کارت نامعتبر است."),
    INVALID_AMOUNT("مبلغ نامعتبر است. مبلغ باید مضربی از 100000 ریال باشد."),
    INSUFFICIENT_FUNDS("موجودی کافی نمی باشد.");
    public static Messages INVALID_CUSTOMER;
    private final String value;

    Messages(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

