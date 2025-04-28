package com.reznikov.paymentservice.domain;

import com.reznikov.paymentservice.utils.Currency;
import com.reznikov.paymentservice.utils.PaymentStatus;
import lombok.*;
import javax.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Entity
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal paymentAmount;

    @Column(nullable = false, length = 3)
    private Currency currency;

    @Column(nullable = false)
    private PaymentStatus status;

    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal energyAmount;

    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal energyPricePerUnit;

    @Column(precision = 10, scale = 2)
    private BigDecimal taxAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal feeAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal discount;

    @Column(nullable = false)
    private String paymentMethod;

    @Column(unique = true)
    private String referenceNumber;

    @Column
    private String invoiceId;

    @Column
    private String provider;

    @Column
    private String remarks;

}

