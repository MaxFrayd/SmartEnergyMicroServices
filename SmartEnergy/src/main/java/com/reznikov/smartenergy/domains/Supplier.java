package com.reznikov.smartenergy.domains;

import com.reznikov.smartenergy.enums.SupplierStatus;

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
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    @OneToOne
    private Address address;
    @Column(nullable = false)
    private Double energyAmount;
    @Column(nullable = false)
    private Double currentEnergyAmount;
    @Column(nullable = false)
    private Double pricePrKwt;

    @Enumerated(EnumType.STRING)
    private SupplierStatus status;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private  LocalDateTime updated;

    public void increaseEnergyAmount(Double amount) {
        currentEnergyAmount += amount;
    }


}