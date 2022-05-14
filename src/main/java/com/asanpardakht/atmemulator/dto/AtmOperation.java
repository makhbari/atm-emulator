package com.asanpardakht.atmemulator.dto;

public enum AtmOperation {
    CHECK_BALANCE("موجودی"),
    CASH_DEPOSIT("واریز"),
    CASH_WITHDRAWAL("برداشت");
    private final String operationTitle;

    AtmOperation(String operationTitle) {
        this.operationTitle = operationTitle;
    }

    public String getOperationTitle() {
        return operationTitle;
    }
}
