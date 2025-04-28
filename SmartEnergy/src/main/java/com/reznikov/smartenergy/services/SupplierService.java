package com.reznikov.smartenergy.services;

import com.reznikov.smartenergy.domains.Supplier;
import com.reznikov.smartenergy.dto.CustomerFullDto;
import com.reznikov.smartenergy.dto.EnergyUpdateDto;
import com.reznikov.smartenergy.dto.SupplierFullDto;
import com.reznikov.smartenergy.dto.SupplierRegDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


import java.util.List;
import java.util.Optional;

public interface SupplierService {
     List<SupplierRegDto> findSuppliersByCriteria(String name, String email);
     String activateSupplier(Long id);
     String addSupplier(SupplierRegDto supplier);

     void updateSupplier(SupplierFullDto supplier);

     List<Supplier> getAllSuppliers();

     SupplierFullDto getSupplierById(Long id);

     void deleteSupplier(Long id);

     Page<Supplier> findBySearchCriteria(Specification<Supplier> spec, Pageable page);

     void releaseSupplierEnergy(Long sid, Long cid);

     List<CustomerFullDto> getCustomersBySupplierId(Long sid);

     void updateSupplierEnergyAmount(EnergyUpdateDto energyUpdateDto);
}
