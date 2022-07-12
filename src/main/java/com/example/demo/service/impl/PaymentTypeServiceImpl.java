package com.example.demo.service.impl;

import com.example.demo.exception.GeneralException;
import com.example.demo.model.dto.PaymentTypeDto;
import com.example.demo.model.entity.PaymentTypeEntity;
import com.example.demo.repository.PaymentTypeRepository;
import com.example.demo.service.PaymentTypeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

import static com.example.demo.model.enumeration.PaymentType.CASH;
import static com.example.demo.model.enumeration.PaymentType.CREDIT_CARD;


@Service
@RequiredArgsConstructor
public class PaymentTypeServiceImpl implements PaymentTypeService {
    private final PaymentTypeRepository paymentTypeRepository;

    private final ModelMapper modelMapper;

    @Override
    public PaymentTypeDto addPaymentType(PaymentTypeDto paymentTypeDto) {
        validatePaymentType(paymentTypeDto, CASH.getValue(), CREDIT_CARD.getValue());

        PaymentTypeEntity paymentTypeEntity = modelMapper.map(paymentTypeDto, PaymentTypeEntity.class);
        paymentTypeRepository.save(paymentTypeEntity);

        return modelMapper.map(paymentTypeEntity, PaymentTypeDto.class);
    }

    private void validatePaymentType(PaymentTypeDto paymentTypeDto, String... status) {
        if (Objects.nonNull(paymentTypeDto) && Arrays.stream(status).noneMatch(s -> s.equals(paymentTypeDto.getPaymentTypeName()))) {
            throw new GeneralException("Invalid payment type");
        }
    }
}
