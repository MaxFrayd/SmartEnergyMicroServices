package com.reznikov.smartenergy.dto;

import com.reznikov.smartenergy.enums.SupplierStatus;
import javax.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierFullDto {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    @Valid
    private AddressDto address;

    @PositiveOrZero(message = "Amount of energy should be zero or positive")
    private double energyAmount;

    private Double currentEnergyAmount;

    private Double pricePrKwt;

    @Enumerated(EnumType.STRING)
    private SupplierStatus status;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private  LocalDateTime updated;

}

