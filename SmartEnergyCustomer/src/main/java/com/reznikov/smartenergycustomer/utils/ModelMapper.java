package com.reznikov.smartenergycustomer.utils;

import com.reznikov.smartenergycustomer.domains.Address;
import com.reznikov.smartenergycustomer.domains.Customer;
import com.reznikov.smartenergycustomer.dto.AddressDto;
import com.reznikov.smartenergycustomer.dto.CustomerFullDto;
import com.reznikov.smartenergycustomer.dto.CustomerRegDto;
import com.reznikov.smartenergycustomer.enums.CustomerStatus;
import org.springframework.stereotype.Component;


@Component
public class ModelMapper {
    public Customer fromFormDto(CustomerRegDto dto) {
        return Customer.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .pricePrKwt(dto.getPricePrKwt())
                .energyAmount(dto.getEnergyAmount())
                .address(fromAddressDto(dto.getAddress()))
                .status(CustomerStatus.NEW)
                .supplierId(dto.getSupplierId())
                .build();
    }
    public CustomerRegDto toFormDto(Customer entity) {
        return CustomerRegDto.builder()
                .name(entity.getName())
                .email(entity.getEmail())
                .address(toAddressDto(entity.getAddress()))
                .energyAmount(entity.getEnergyAmount())
                .supplierId(entity.getSupplierId())
                .build();
    }

    public Address fromAddressDto(AddressDto dto) {
        return new Address(dto.getId(), dto.getLatitude(), dto.getLongitude(), dto.getCity());
    }
    public AddressDto toAddressDto(Address address) {
        return new AddressDto(address.getId(), address.getLatitude(), address.getLongitude(), address.getCity());
    }

    public CustomerFullDto toFullDto(Customer entity) {
        return CustomerFullDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .address(toAddressDto(entity.getAddress()))
                .energyAmount(entity.getEnergyAmount())
                .pricePrKwt(entity.getPricePrKwt())
                .supplierId(entity.getSupplierId())
                //ADD other fields
                .build();
    }

    public Customer fromFullDto(CustomerFullDto dto) {
        return Customer.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .address(fromAddressDto(dto.getAddress()))
                .energyAmount(dto.getEnergyAmount())
                .pricePrKwt(dto.getPricePrKwt())
                .supplierId(dto.getSupplierId())
                //ADD other fields
                .build();
    }
}
