package com.example.demo.model.dto;

import lombok.Data;

@Data
public class ReceiptDto {

    private String productName;
    private Integer quantity;
    private String paymentType;
    private Double refund;

}
