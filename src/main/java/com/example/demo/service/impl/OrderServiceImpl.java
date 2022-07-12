package com.example.demo.service.impl;

import com.example.demo.exception.GeneralException;
import com.example.demo.model.dto.PaymentTypeDto;
import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.dto.ReceiptDto;
import com.example.demo.model.entity.OrderItemEntity;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

import static com.example.demo.model.enumeration.OrderItemStatus.PROCESS_COMPLETED;
import static com.example.demo.model.enumeration.OrderItemStatus.TRANSACTION_IN_PROGRESS;
import static com.example.demo.model.enumeration.OrderItemSubStatus.*;
import static com.example.demo.model.enumeration.PaymentType.*;
import static com.example.demo.model.enumeration.ProductType.COLD_DRINK;
import static com.example.demo.model.enumeration.ProductType.HOT_DRINK;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    private static final String ORDER_NOT_FOUND = "Order not found...";


    @Override
    public String selectProduct(Integer productId) {
        ProductDto productDto = productRepository.findByProductId(productId);
        String status = orderItemRepository.getStatusByProductId(productId);

        if (Objects.isNull(productDto)) {
            throw new GeneralException("Product not found.");
        }

        if (Objects.nonNull(status)) {
            throw new GeneralException("There is an ongoing transaction...");
        }

        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setProductName(productDto.getProductName());
        orderItemEntity.setProductType(productDto.getProductType());
        orderItemEntity.setProductId(productId);
        orderItemEntity.setStatus(TRANSACTION_IN_PROGRESS.getValue());
        orderItemEntity.setSubStatus(PRODUCT_SELECTED.getValue());
        orderItemRepository.save(orderItemEntity);
        return productDto.getProductName();
    }

    @Override
    public Integer selectProductQuantity(Integer quantity) {
        OrderItemEntity ordItemEntity = orderItemRepository.findFirstByStatusOrderByIdDesc(TRANSACTION_IN_PROGRESS.getValue());

        if (Objects.isNull(ordItemEntity)) {
            throw new GeneralException(ORDER_NOT_FOUND);
        }

        validateOrderSubStatus(ordItemEntity, PRODUCT_SELECTED.getValue());
        ordItemEntity.setQuantity(quantity);
        ordItemEntity.setSubStatus(PRODUCT_QUANTITY_SELECTED.getValue());
        orderItemRepository.save(ordItemEntity);
        return quantity;
    }

    @Override
    public Integer selectSugarQuantity(Integer sugarQuantity, Integer productId) {
        OrderItemEntity orderItem = orderItemRepository.findFirstByStatusOrderByIdDesc(TRANSACTION_IN_PROGRESS.getValue());

        if (Objects.isNull(orderItem)) {
            throw new GeneralException(ORDER_NOT_FOUND);
        }

        validateOrderSubStatus(orderItem, PRODUCT_QUANTITY_SELECTED.getValue());

        if (orderItem.getProductType().equals(HOT_DRINK.getValue())) {
            orderItem.setSugarQuantity(sugarQuantity);
            orderItem.setSubStatus(SUGAR_QUANTITY_SELECTED.getValue());
            orderItemRepository.save(orderItem);
            return sugarQuantity;
        }
        throw new GeneralException("Sugar only selected with hot drinks...");
    }

    @Override
    public PaymentTypeDto selectPaymentType(PaymentTypeDto paymentTypeDto) {
        OrderItemEntity ordItemEntity = orderItemRepository.findFirstByStatusOrderByIdDesc(TRANSACTION_IN_PROGRESS.getValue());

        if (Objects.isNull(ordItemEntity)) {
            throw new GeneralException(ORDER_NOT_FOUND);
        }

        if (ordItemEntity.getProductType().equals(COLD_DRINK.getValue())) {
            validateOrderSubStatus(ordItemEntity, PRODUCT_QUANTITY_SELECTED.getValue());
        } else if (ordItemEntity.getProductType().equals(HOT_DRINK.getValue())) {
            validateOrderSubStatus(ordItemEntity, SUGAR_QUANTITY_SELECTED.getValue());
        }

        ordItemEntity.setPaymentType(paymentTypeDto.getPaymentTypeName());
        if (ordItemEntity.getPaymentType().equals(CREDIT_CARD.getValue())) {
            ordItemEntity.setStatus(PROCESS_COMPLETED.getValue());
            ordItemEntity.setSubStatus(CREDIT_CARD_PAYMENT_TYPE_SELECTED.getValue());
        } else {
            ordItemEntity.setSubStatus(CASH_PAYMENT_TYPE_SELECTED.getValue());
        }
        orderItemRepository.save(ordItemEntity);
        return paymentTypeDto;
    }

    @Override
    public Double refundMoney(Double money, Integer productId) {
        OrderItemEntity ordItem = orderItemRepository.findFirstByStatusOrderByIdDesc(TRANSACTION_IN_PROGRESS.getValue());

        if (Objects.isNull(ordItem)) {
            throw new GeneralException(ORDER_NOT_FOUND);
        }

        validateOrderSubStatus(ordItem, CASH_PAYMENT_TYPE_SELECTED.getValue());
        String paymentType = ordItem.getPaymentType();

        ProductDto productDto = productRepository.findByProductId(productId);

        if (paymentType.equals(CASH.getValue())) {
            Double totalMoney = productDto.getPrice() * ordItem.getQuantity();
            if (money < totalMoney) {
                throw new GeneralException("Insufficient balance...");
            }
            Double refund = money - totalMoney;
            ordItem.setRefund(refund);
            ordItem.setStatus(PROCESS_COMPLETED.getValue());
            ordItem.setSubStatus(REFUND_MONEY.getValue());
            orderItemRepository.save(ordItem);
            return refund;
        }
        throw new GeneralException("Only refund money transaction...");
    }

    @Override
    public ReceiptDto receipt(Integer productId) {
        OrderItemEntity orderItem = orderItemRepository.findByProductId(productId);

        if (Objects.isNull(orderItem)) {
            throw new GeneralException(ORDER_NOT_FOUND);
        }

        validateOrderSubStatus(orderItem, REFUND_MONEY.getValue(), CREDIT_CARD_PAYMENT_TYPE_SELECTED.getValue());

        orderItem.setSubStatus(RECEIPT.getValue());
        orderItemRepository.save(orderItem);
        ReceiptDto receiptDto = new ReceiptDto();
        receiptDto.setProductName(orderItem.getProductName());
        receiptDto.setQuantity(orderItem.getQuantity());
        receiptDto.setPaymentType(orderItem.getPaymentType());
        if (orderItem.getRefund() != null) {
            receiptDto.setRefund(orderItem.getRefund());
        }
        return receiptDto;

    }

    private void validateOrderSubStatus(OrderItemEntity orderItemEntity, String... status) {
        if (Objects.nonNull(orderItemEntity) && Arrays.stream(status).noneMatch(s -> s.equals(orderItemEntity.getSubStatus()))) {
            throw new GeneralException("You cannot process with this sub status...");
        }
    }

}
