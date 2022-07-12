package com.example.demo.service.impl;

import com.example.demo.exception.GeneralException;
import com.example.demo.model.dto.PaymentTypeDto;
import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.dto.ReceiptDto;
import com.example.demo.model.entity.OrderItemEntity;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static com.example.demo.model.enumeration.PaymentType.CASH;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    ProductRepository productRepository;

    @Mock
    OrderItemRepository orderItemRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void whenSelectProductWithValidRequest_itShouldReturnValidResponse() {
        Integer productId = 1;
        ProductDto productDto = new ProductDto("TEA", "HOT DRINK", 5.12);
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setProductName("TEA");
        orderItemEntity.setProductType("HOT DRINK");
        orderItemEntity.setProductId(productId);
        orderItemEntity.setStatus("TRANSACTION IN PROGRESS");
        orderItemEntity.setSubStatus("PRODUCT SELECTED");

        Mockito.when(productRepository.findByProductId(productId)).thenReturn(productDto);
        Mockito.when(orderItemRepository.save(orderItemEntity)).thenReturn(orderItemEntity);

        String result = orderService.selectProduct(1);
        String expected = productDto.getProductName();

        assertEquals(expected, result);

    }

    @Test
    void whenSelectProductWithValidRequest_thenThrowOngoingTransaction() {
        Mockito.when(productRepository.findByProductId(Mockito.anyInt())).thenReturn(new ProductDto());
        Mockito.when(orderItemRepository.getStatusByProductId(Mockito.anyInt())).thenReturn("TRANSACTION IN PROGRESS");

        assertThrows(GeneralException.class, () -> orderService.selectProduct(1));
    }


    @Test
    void whenSelectProductQuantityWithValidRequest_itShouldReturnValidResponse() {
        OrderItemEntity ordItemEntity = new OrderItemEntity();
        ordItemEntity.setStatus("TRANSACTION IN PROGRESS");
        ordItemEntity.setQuantity(3);
        ordItemEntity.setSubStatus("PRODUCT SELECTED");

        Mockito.when(orderItemRepository.findFirstByStatusOrderByIdDesc("TRANSACTION IN PROGRESS")).thenReturn(ordItemEntity);
        Mockito.when(orderItemRepository.save(ordItemEntity)).thenReturn(ordItemEntity);

        Integer result = orderService.selectProductQuantity(3);
        Integer expected = ordItemEntity.getQuantity();

        assertEquals(expected, result);

    }

    @Test
    void whenSelectProductQuantityWithValidRequest_thenThrowOrderNotFound() {
        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setStatus("bbb");
        Mockito.when(orderItemRepository.findFirstByStatusOrderByIdDesc("aaa")).thenReturn(orderItem);
        assertThrows(GeneralException.class, () -> orderService.selectProductQuantity(2));
    }

    @Test
    void whenSelectProductQuantityWithValidRequest_thenNotValidSubStatus() {
        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setStatus("bbb");
        Mockito.when(orderItemRepository.findFirstByStatusOrderByIdDesc(Mockito.anyString())).thenReturn(orderItem);
        assertThrows(GeneralException.class, () -> orderService.selectProductQuantity(2));
    }


    @Test
    void selectSugarQuantityWithValidRequest_itShouldReturnValidResponse() {
        Integer productId = 1;
        OrderItemEntity ordItemEntity = new OrderItemEntity();
        ordItemEntity.setStatus("TRANSACTION IN PROGRESS");
        ordItemEntity.setProductType("HOT DRINK");
        if (ordItemEntity.getProductType().equals("HOT DRINK")) {
            ordItemEntity.setSugarQuantity(3);
        }

        ordItemEntity.setSubStatus("PRODUCT QUANTITY SELECTED");

        Mockito.when(orderItemRepository.findFirstByStatusOrderByIdDesc("TRANSACTION IN PROGRESS")).thenReturn(ordItemEntity);
        Mockito.when(orderItemRepository.save(ordItemEntity)).thenReturn(ordItemEntity);

        Integer result = orderService.selectSugarQuantity(3, productId);
        Integer expected = ordItemEntity.getSugarQuantity();

        assertEquals(expected, result);
    }

    @Test
    void selectSugarQuantityWithValidRequest_thenThrowOrderNotFound() {
        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setStatus("bbb");
        Mockito.when(orderItemRepository.findFirstByStatusOrderByIdDesc("aaa")).thenReturn(orderItem);
        assertThrows(GeneralException.class, () -> orderService.selectSugarQuantity(2, 1));
    }

    @Test
    void selectPaymentTypeWithValidRequest_itShouldReturnValidResponse1() {
        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setStatus("TRANSACTION IN PROGRESS");
        orderItem.setProductType("COLD DRINK");
        orderItem.setSubStatus("PRODUCT QUANTITY SELECTED");
        orderItem.setPaymentType("CREDIT CARD");

        if (orderItem.getProductType().equals("CREDIT CARD")) {
            orderItem.setStatus("PROCESS COMPLETED");
            orderItem.setSubStatus("CREDIT CARD PAYMENT TYPE SELECTED");
        }

        PaymentTypeDto paymentTypeDto = new PaymentTypeDto("CREDIT CARD", "CONTAC");
        Mockito.when(orderItemRepository.findFirstByStatusOrderByIdDesc("TRANSACTION IN PROGRESS")).thenReturn(orderItem);
        Mockito.when(orderItemRepository.save(orderItem)).thenReturn(orderItem);

        PaymentTypeDto result = orderService.selectPaymentType(paymentTypeDto);

        assertEquals(paymentTypeDto, result);

    }


    @Test
    void selectPaymentTypeWithValidRequest_itShouldReturnValidResponse2() {
        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setStatus("TRANSACTION IN PROGRESS");
        orderItem.setProductType("COLD DRINK");
        orderItem.setSubStatus("PRODUCT QUANTITY SELECTED");
        orderItem.setPaymentType("CREDIT CARD");

        if (orderItem.getProductType().equals("CASH")) {
            orderItem.setSubStatus("CASH PAYMENT TYPE SELECTED");
        }

        PaymentTypeDto paymentTypeDto = new PaymentTypeDto("CASH", "PAPER MONEY");
        Mockito.when(orderItemRepository.findFirstByStatusOrderByIdDesc("TRANSACTION IN PROGRESS")).thenReturn(orderItem);
        Mockito.when(orderItemRepository.save(orderItem)).thenReturn(orderItem);

        PaymentTypeDto result = orderService.selectPaymentType(paymentTypeDto);

        assertEquals(paymentTypeDto, result);

    }

    @Test
    void selectPaymentTypeWithValidRequest_itShouldReturnValidResponse3() {
        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setStatus("TRANSACTION IN PROGRESS");
        orderItem.setProductType("HOT DRINK");
        orderItem.setSubStatus("SUGAR QUANTITY SELECTED");
        orderItem.setPaymentType("CREDIT CARD");

        if (orderItem.getProductType().equals("CREDIT CARD")) {
            orderItem.setSubStatus("PROCESS COMPLETED");
            orderItem.setSubStatus("CREDIT CARD PAYMENT TYPE SELECTED");
        }

        PaymentTypeDto paymentTypeDto = new PaymentTypeDto("CREDIT CARD", "CONTAC");
        Mockito.when(orderItemRepository.findFirstByStatusOrderByIdDesc("TRANSACTION IN PROGRESS")).thenReturn(orderItem);
        Mockito.when(orderItemRepository.save(orderItem)).thenReturn(orderItem);

        PaymentTypeDto result = orderService.selectPaymentType(paymentTypeDto);

        assertEquals(paymentTypeDto, result);

    }

    @Test
    void selectPaymentTypeWithValidRequest_itShouldReturnValidResponse4() {
        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setStatus("TRANSACTION IN PROGRESS");
        orderItem.setProductType("HOT DRINK");
        orderItem.setSubStatus("SUGAR QUANTITY SELECTED");
        orderItem.setPaymentType("CASH");

        if (orderItem.getProductType().equals("CASH")) {
            orderItem.setSubStatus("CASH PAYMENT TYPE SELECTED");
        }

        PaymentTypeDto paymentTypeDto = new PaymentTypeDto("CASH", "PAPER MONEY");
        Mockito.when(orderItemRepository.findFirstByStatusOrderByIdDesc("TRANSACTION IN PROGRESS")).thenReturn(orderItem);
        Mockito.when(orderItemRepository.save(orderItem)).thenReturn(orderItem);

        PaymentTypeDto result = orderService.selectPaymentType(paymentTypeDto);

        assertEquals(paymentTypeDto, result);

    }

    @Test
    void selectPaymentTypeWithValidRequest_thenThrowOrderNotFound() {
        PaymentTypeDto paymentTypeDto = new PaymentTypeDto("CREDIT CARD", "CONTAC");
        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setStatus("bbb");
        Mockito.when(orderItemRepository.findFirstByStatusOrderByIdDesc("aaa")).thenReturn(orderItem);
        assertThrows(GeneralException.class, () -> orderService.selectPaymentType(paymentTypeDto));
    }

    @Test
    void refundMoneyWithValidRequest_itShouldReturnValidResponse() {
        Double money = 90.0;
        Integer productId = 1;

        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setPaymentType("CASH");
        orderItem.setQuantity(3);
        orderItem.setStatus("TRANSACTION IN PROGRESS");
        orderItem.setSubStatus("CASH PAYMENT TYPE SELECTED");

        ProductDto productDto = new ProductDto("TEA", "HOT DRINK", 5.12);

        Mockito.when(orderItemRepository.findFirstByStatusOrderByIdDesc("TRANSACTION IN PROGRESS")).thenReturn(orderItem);
        Mockito.when(productRepository.findByProductId(productId)).thenReturn(productDto);

        if (orderItem.getPaymentType().equals(CASH.getValue())) {
            Double totalMoney = (orderItem.getQuantity() * productDto.getPrice());
            Double refund = money - totalMoney;
            orderItem.setRefund(refund);
        }
        Mockito.when(orderItemRepository.save(orderItem)).thenReturn(orderItem);

        Double result = orderService.refundMoney(money, productId);
        Double expected = orderItem.getRefund();

        assertEquals(expected, result);

    }

    @Test
    void refundMoneyWithValidRequest_thenThrowOrderNotFound() {
        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setStatus("bbb");
        Mockito.when(orderItemRepository.findFirstByStatusOrderByIdDesc("aaa")).thenReturn(orderItem);
        assertThrows(GeneralException.class, () -> orderService.refundMoney(90.0, 1));
    }

    @Test
    void receiptWithValidRequest_itShouldReturnValidResponse() {
        Integer productId = 1;
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setSubStatus("REFUND MONEY");
        orderItemEntity.setProductId(productId);
        orderItemEntity.setProductName("ICE TEA");
        orderItemEntity.setQuantity(2);
        orderItemEntity.setPaymentType("CREDIT CARD");

        ReceiptDto receiptDto = new ReceiptDto();
        receiptDto.setProductName("ICE TEA");
        receiptDto.setQuantity(2);
        receiptDto.setPaymentType("CREDIT CARD");

        Mockito.when(orderItemRepository.findByProductId(productId)).thenReturn(orderItemEntity);

        ReceiptDto result = orderService.receipt(productId);

        assertEquals(receiptDto, result);

    }

    @Test
    void receiptWithValidRequest_thenThrowOrderNotFound() {
        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setStatus("bbb");
        Mockito.when(orderItemRepository.findFirstByStatusOrderByIdDesc("aaa")).thenReturn(orderItem);
        assertThrows(GeneralException.class, () -> orderService.receipt(1));
    }
}