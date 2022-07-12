package com.example.demo.service.impl;

import com.example.demo.exception.GeneralException;
import com.example.demo.model.dto.PaymentTypeDto;
import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.entity.ProductEntity;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;


import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenAddProductWithValidRequest_itShouldReturnValidDto() {
        ProductDto productDto = new ProductDto("TEA", "HOT DRINK", 5.12);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName("TEA");
        productEntity.setProductType("HOT DRINK");
        productEntity.setPrice(5.12);

        Mockito.when(modelMapper.map(productDto, ProductEntity.class)).thenReturn(productEntity);
        Mockito.when(productRepository.save(productEntity)).thenReturn(productEntity);
        Mockito.when(modelMapper.map(productEntity, ProductDto.class)).thenReturn(productDto);

        ProductDto result = productService.addProduct(productDto);

        assertEquals(productDto, result);

    }

    @Test
    void whenAddProductWithValidRequest_thenThrowProductTypeGeneralException() {
        ProductDto productDto = new ProductDto("TEA", "aaaa", 5.12);

        assertThrows(GeneralException.class, () -> productService.addProduct(productDto));
    }

}