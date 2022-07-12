package com.example.demo.controller;

import com.example.demo.exception.GeneralException;
import com.example.demo.model.dto.PaymentTypeDto;
import com.example.demo.service.PaymentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/paymentType")
@RequiredArgsConstructor
public class PaymentTypeController {

    private final PaymentTypeService paymentTypeService;

    @PostMapping(value = "/add")
    public ResponseEntity<Object> addPaymentType(@RequestBody PaymentTypeDto paymentTypeRequestDto) {
        try {
            return ResponseEntity.ok(paymentTypeService.addPaymentType(paymentTypeRequestDto));
        } catch (GeneralException exception) {
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }
}
