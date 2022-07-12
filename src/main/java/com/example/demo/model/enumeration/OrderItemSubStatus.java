package com.example.demo.model.enumeration;

public enum OrderItemSubStatus {

    PRODUCT_SELECTED("PRODUCT SELECTED"),
    PRODUCT_QUANTITY_SELECTED("PRODUCT QUANTITY SELECTED"),
    SUGAR_QUANTITY_SELECTED("SUGAR QUANTITY SELECTED"),
    CREDIT_CARD_PAYMENT_TYPE_SELECTED("CREDIT CARD PAYMENT TYPE SELECTED"),
    CASH_PAYMENT_TYPE_SELECTED("CASH PAYMENT TYPE SELECTED"),
    REFUND_MONEY("REFUND MONEY"),
    RECEIPT("RECEIPT");


    OrderItemSubStatus(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }

}
