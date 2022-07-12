package com.example.demo.model.enumeration;

public enum ProductType {
    HOT_DRINK("HOT DRINK"),
    COLD_DRINK("COLD DRINK"),
    FOOD("FOOD");

    private final String value;

    ProductType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
