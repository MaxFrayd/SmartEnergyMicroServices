package com.reznikov.smartenergy.events.model;

import lombok.*;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierEnergyChangeModel {
  @Id
  private Long supplierId;
  private String supplierName;
  private Double updatedEnergyAmount;
  private Double currentEnergyAmount;
  //private Double pricePerKwt;
  private String status;
  private String timestamp;
}
