package com.example.demo.model.enumeration;

public enum PaymentType {

    CASH("CASH"),
    CREDIT_CARD("CREDIT CARD");

    private final String value;

    PaymentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
