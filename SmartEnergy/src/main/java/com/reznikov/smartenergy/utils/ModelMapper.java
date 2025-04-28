package com.reznikov.smartenergy.utils;

import com.reznikov.smartenergy.domains.Address;
import com.reznikov.smartenergy.domains.Supplier;
import com.reznikov.smartenergy.dto.AddressDto;
import com.reznikov.smartenergy.dto.CustomerFullDto;
import com.reznikov.smartenergy.dto.SupplierFullDto;
import com.reznikov.smartenergy.dto.SupplierRegDto;
import com.reznikov.smartenergy.enums.SupplierStatus;
import com.reznikov.smartenergy.events.model.SupplierEnergyChangeModel;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper {
    public Supplier fromFormDto(SupplierRegDto dto) {
//        return new Supplier(dto.getName(),
//                dto.getEmail(),
//                fromAddressDto(dto.getAddress()),
//                dto.getEnergyAmount(),
//                SupplierStatus.NEW);
//                dto.getEnergyAmount(),
//                dto.getPricePrKwt()

        return Supplier.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .pricePrKwt(dto.getPricePrKwt())
                .energyAmount(dto.getEnergyAmount())
                .address(fromAddressDto(dto.getAddress()))
                .status(SupplierStatus.NEW)
                .currentEnergyAmount(dto.getEnergyAmount())



                .build();
    }
    public SupplierRegDto toFormDto(Supplier entity) {
        return SupplierRegDto.builder()
                .name(entity.getName())
                .email(entity.getEmail())
                .address(toAddressDto(entity.getAddress()))
                .energyAmount(entity.getEnergyAmount())
//                entity.getName(),
//                entity.getEmail(),
//                toAddressDto(entity.getAddress()),
//                entity.getEnergyAmount()
//                );
                .build();
    }

    public Address fromAddressDto(AddressDto dto) {
        return new Address(dto.getId(), dto.getLatitude(), dto.getLongitude(), dto.getCity());
    }
    public AddressDto toAddressDto(Address address) {
        return new AddressDto(address.getId(), address.getLatitude(), address.getLongitude(), address.getCity());
    }
    public SupplierFullDto toFullDto (Supplier entity) {
        return new SupplierFullDto(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                toAddressDto(entity.getAddress()),
                entity.getEnergyAmount(),
                entity.getCurrentEnergyAmount(),
                entity.getPricePrKwt(),
                entity.getStatus(),
                entity.getCreated(),
                entity.getUpdated()
        );
    }

    public SupplierEnergyChangeModel toEnergyChangeModel (SupplierFullDto dto, Double updatedEnergyAmount) {
        return new SupplierEnergyChangeModel(
                dto.getId(),
                dto.getName(),
                updatedEnergyAmount,
                dto.getEnergyAmount(),
                dto.getStatus().toString(),
                dto.getUpdated().toString()

        );
    }

    public Supplier fromSupplierFullDto(SupplierFullDto dto) {
        if(dto == null){
            return null;
        }
        return new Supplier(
                dto.getId(),
                dto.getName(),
                dto.getEmail(),
                fromAddressDto(dto.getAddress()),
                dto.getEnergyAmount(),
                dto.getCurrentEnergyAmount(),
                dto.getPricePrKwt(),
                dto.getStatus(),
                dto.getCreated(),
                dto.getUpdated()
        );
    }
    public Supplier updateSupplierFromDto(SupplierFullDto dto, Supplier existingSupplier) {
        if (dto == null || existingSupplier == null) {
            return null;
        }

        // Only update if the DTO value is not null
        if (dto.getName() != null) {
            existingSupplier.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            existingSupplier.setEmail(dto.getEmail());
        }
        if (dto.getAddress() != null) {
            existingSupplier.setAddress(fromAddressDto(dto.getAddress()));
        }
        if (dto.getEnergyAmount() > 0) {
            existingSupplier.setEnergyAmount(dto.getEnergyAmount());
        }
        if (dto.getCurrentEnergyAmount() != null) {
            existingSupplier.setCurrentEnergyAmount(dto.getCurrentEnergyAmount());
        }
        if (dto.getPricePrKwt() != null) {
            existingSupplier.setPricePrKwt(dto.getPricePrKwt());
        }
        if (dto.getStatus() != null) {
            existingSupplier.setStatus(dto.getStatus());
        }
        if (dto.getUpdated() != null) {
            existingSupplier.setUpdated(dto.getUpdated());
        }

        // Return the updated entity
        return existingSupplier;
    }
}
