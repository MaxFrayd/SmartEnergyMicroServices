package com.reznikov.smartenergycustomer.repositories;

import com.reznikov.smartenergycustomer.domains.Customer;

import java.util.List;

import com.reznikov.smartenergycustomer.dto.CustomerFullDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
     Customer findByEmail(String email);
     List<Customer> findAllByNameAndEmail(String name, String email);
     List<Customer> findAllBySupplierId(Long supplierId);

}
