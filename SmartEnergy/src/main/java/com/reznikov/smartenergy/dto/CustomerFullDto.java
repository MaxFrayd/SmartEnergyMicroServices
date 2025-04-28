package com.reznikov.smartenergy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@RedisHash("CustomerFullDto")
public class CustomerFullDto {

    private Long id;


    private String name;


    private String email;


    private AddressDto address;

    private Long supplierId;


    private double energyAmount;

    private Double pricePrKwt;

    private CustomerStatus status;


    private LocalDateTime created;


    private LocalDateTime updated;

    public static enum CustomerStatus {
        ACTIVE,
        INACTIVE,
        NEW
    }

}

