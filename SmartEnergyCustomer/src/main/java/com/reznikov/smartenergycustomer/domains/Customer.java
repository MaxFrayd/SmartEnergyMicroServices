package com.reznikov.smartenergycustomer.domains;

import com.reznikov.smartenergycustomer.enums.CustomerStatus;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    @OneToOne
    private Address address;

    @Column(name = "supplier_id")
    private Long supplierId;
    @Column(nullable = false)
    private Double energyAmount;

    @Column(nullable = false)
    private Double pricePrKwt;

    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private  LocalDateTime updated;
}