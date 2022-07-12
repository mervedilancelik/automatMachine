package com.example.demo.service.impl;

import com.example.demo.exception.GeneralException;
import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.entity.ProductEntity;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.Objects;

import static com.example.demo.model.enumeration.ProductType.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    private static final Integer DRINK_SLOT_SIZE = 20;
    private static final Integer FOOD_SLOT_SIZE = 10;

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        validateProductType(productDto, HOT_DRINK.getValue(), COLD_DRINK.getValue(), FOOD.getValue());
        validateSlots(productDto);

        ProductEntity productEntity = modelMapper.map(productDto, ProductEntity.class);

        productRepository.save(productEntity);

        return modelMapper.map(productEntity, ProductDto.class);

    }

    private void validateSlots(ProductDto productRequestDto) {
        if (productRequestDto.getProductType().equals(HOT_DRINK.getValue()) || productRequestDto.getProductType().equals(COLD_DRINK.getValue())) {
            long drinkCount = productRepository.countAll(HOT_DRINK.getValue()) + productRepository.countAll(COLD_DRINK.getValue());

            if (drinkCount >= DRINK_SLOT_SIZE) {
                throw new GeneralException("Drink slot limit is full...");
            }
        } else {
            Long foodCount = productRepository.countAll(FOOD.getValue());

            if (foodCount >= FOOD_SLOT_SIZE) {
                throw new GeneralException("Food slot limit is full...");
            }
        }
    }

    private void validateProductType(ProductDto productRequestDt, String... status) {
        if (Objects.nonNull(productRequestDt) && Arrays.stream(status).noneMatch(s -> s.equals(productRequestDt.getProductType()))) {
            throw new GeneralException("Invalid product type...");
        }
    }
}