package com.example.demo.model.enumeration;

public enum OrderItemStatus {

    TRANSACTION_IN_PROGRESS("TRANSACTION IN PROGRESS"),
    PROCESS_COMPLETED("PROCESS COMPLETED");

    OrderItemStatus(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }

}
