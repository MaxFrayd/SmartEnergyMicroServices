package com.reznikov.smartenergy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EnergyUpdateDto {
    private SupplierFullDto supplierFullDto;
    private Double updatedEnergy;
}
