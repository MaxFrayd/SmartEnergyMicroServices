package com.reznikov.smartenergycustomer.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Id;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@RedisHash("SupplierFullDto")
public class SupplierFullDto extends RepresentationModel<SupplierFullDto> {

    @Id
    private Long id;

    private String name;

    private String email;

    private AddressDto address;

    private double energyAmount;

    private String status;

    private LocalDateTime created;

    private  LocalDateTime updated;

}

