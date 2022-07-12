package com.example.demo.model.entity;

import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "ORDER_ITEM")
@Data
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PRODUCT_ID")
    private Integer productId;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "PRODUCT_TYPE")
    private String productType;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "SUGAR_QUANTITY")
    private Integer sugarQuantity;

    @Column(name = "PAYMENT_TYPE")
    private String paymentType;

    @Column(name = "REFUND")
    private Double refund;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "SUB_STATUS")
    private String subStatus;

}
