package com.example.demo.controller;

import com.example.demo.exception.GeneralException;
import com.example.demo.model.dto.ProductDto;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "/add")
    public ResponseEntity<Object> addProduct(@RequestBody ProductDto productDto) {
        try {
            return new ResponseEntity<>(productService.addProduct(productDto), HttpStatus.OK);
        } catch (GeneralException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
