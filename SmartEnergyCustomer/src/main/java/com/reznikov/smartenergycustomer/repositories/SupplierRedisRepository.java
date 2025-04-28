package com.reznikov.smartenergycustomer.repositories;

import com.reznikov.smartenergycustomer.dto.SupplierFullDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRedisRepository extends CrudRepository<SupplierFullDto, Long> {
}
