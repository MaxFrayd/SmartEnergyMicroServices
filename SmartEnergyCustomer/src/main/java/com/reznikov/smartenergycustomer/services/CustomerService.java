package com.reznikov.smartenergycustomer.services;

import com.reznikov.smartenergycustomer.domains.Customer;
import com.reznikov.smartenergycustomer.dto.CustomerFullDto;
import com.reznikov.smartenergycustomer.dto.CustomerRegDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


import java.util.List;
import java.util.Optional;

public interface CustomerService {
     List<CustomerRegDto> findSuppliersByCriteria(String name, String email);
     String activateSupplier(Long id);

     String addCustomer(CustomerRegDto supplier);

     void updateCustomer(CustomerFullDto customer);

     List<Customer> getAllSuppliers();

     Optional<Customer> getSupplierById(Long id);

     void deleteSupplier(Long id);

     Page<Customer> findBySearchCriteria(Specification<Customer> spec, Pageable page);

     List<CustomerFullDto> getCustomersBySupplier(Long supplierId);
}
