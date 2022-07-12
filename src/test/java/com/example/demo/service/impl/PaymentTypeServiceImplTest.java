package com.example.demo.service.impl;

import com.example.demo.exception.GeneralException;
import com.example.demo.model.dto.PaymentTypeDto;
import com.example.demo.model.entity.PaymentTypeEntity;
import com.example.demo.repository.PaymentTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentTypeServiceImplTest {

    @InjectMocks
    PaymentTypeServiceImpl paymentTypeService;

    @Mock
    PaymentTypeRepository paymentTypeRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenAddPaymentTypeWithValidRequest_itShouldReturnValidDto() {

        PaymentTypeDto paymentTypeDto = new PaymentTypeDto("CASH", "PAPER MONEY");

        PaymentTypeEntity paymentTypeEntity = new PaymentTypeEntity();
        paymentTypeEntity.setPaymentTypeName("CASH");
        paymentTypeEntity.setType("PAPER MONEY");

        Mockito.when(modelMapper.map(paymentTypeDto, PaymentTypeEntity.class)).thenReturn(paymentTypeEntity);
        Mockito.when(paymentTypeRepository.save(paymentTypeEntity)).thenReturn(paymentTypeEntity);
        Mockito.when(modelMapper.map(paymentTypeEntity, PaymentTypeDto.class)).thenReturn(paymentTypeDto);

        PaymentTypeDto result = paymentTypeService.addPaymentType(paymentTypeDto);

        assertEquals(paymentTypeDto, result);

    }

    @Test
    void whenAddPaymentTypeWithValidRequest_thenThrowGeneralException() {
        PaymentTypeDto paymentTypeDto = new PaymentTypeDto("abc", "PAPER MONEY");

        assertThrows(GeneralException.class, () -> paymentTypeService.addPaymentType(paymentTypeDto));
    }

}