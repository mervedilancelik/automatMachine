package com.example.demo.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "PAYMENT_TYPE")
@Data
public class PaymentTypeEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME")
    private String paymentTypeName;

    @Column(name = "TYPE")
    private String type;


}
