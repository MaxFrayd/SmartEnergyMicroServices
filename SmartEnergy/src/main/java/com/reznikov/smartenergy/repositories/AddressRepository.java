package com.reznikov.smartenergy.repositories;

import com.reznikov.smartenergy.domains.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
