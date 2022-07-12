package com.example.demo.controller;

import com.example.demo.exception.GeneralException;
import com.example.demo.model.dto.PaymentTypeDto;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/select-product")
    public ResponseEntity<String> selectProduct(@RequestParam("productId") Integer productId) {
        try {
            return ResponseEntity.ok(orderService.selectProduct(productId));
        } catch (GeneralException exception) {
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @PostMapping(value = "/select-productQuantity")
    public ResponseEntity<Object> selectProductQuantity(@RequestParam("quantity") Integer quantity) {
        try {
            return ResponseEntity.ok(orderService.selectProductQuantity(quantity));
        } catch (GeneralException exception) {
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @PostMapping(value = "/select-sugarQuantity")
    public ResponseEntity<Object> selectSugarQuantity(@RequestParam Integer sugarQuantity, @RequestParam Integer productId) {
        try {
            return ResponseEntity.ok(orderService.selectSugarQuantity(sugarQuantity, productId));
        } catch (GeneralException exception) {
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @PostMapping(value = "/select-paymentType")
    public ResponseEntity<Object> selectPaymentType(@RequestBody PaymentTypeDto paymentTypeDto) {
        try {
            return ResponseEntity.ok(orderService.selectPaymentType(paymentTypeDto));
        } catch (GeneralException exception) {
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @PostMapping(value = "/refund")
    public ResponseEntity<Object> refund(@RequestParam Double money, @RequestParam Integer productId) {
        try {
            return ResponseEntity.ok(orderService.refundMoney(money, productId));
        } catch (GeneralException exception) {
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @GetMapping(value = "/receipt")
    public ResponseEntity<Object> receipt(@RequestParam("productId") Integer productId) {
        try {
            return ResponseEntity.ok(orderService.receipt(productId));
        } catch (GeneralException exception) {
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }
}
