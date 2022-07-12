package com.example.demo.service;

import com.example.demo.model.dto.PaymentTypeDto;
import com.example.demo.model.dto.ReceiptDto;

public interface OrderService {
    String selectProduct(Integer productId);

    Integer selectProductQuantity(Integer quantity);

    Integer selectSugarQuantity(Integer sugarQuantity, Integer productId);

    PaymentTypeDto selectPaymentType(PaymentTypeDto paymentTypeDto);

    Double refundMoney(Double money, Integer productId);

    ReceiptDto receipt(Integer productId);
}
