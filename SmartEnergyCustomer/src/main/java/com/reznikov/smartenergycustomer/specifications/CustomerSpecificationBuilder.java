package com.reznikov.smartenergycustomer.specifications;

import com.reznikov.smartenergycustomer.domains.Customer;
import com.reznikov.smartenergycustomer.dto.CustomerSearchDto;
import com.reznikov.smartenergycustomer.enums.SearchOperation;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CustomerSpecificationBuilder {

    private final List<SearchCriteria<?>> params;

    public CustomerSpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public final <T extends Comparable<T>> CustomerSpecificationBuilder with(String key, String operation, T value) {
        params.add(new SearchCriteria<>(key, operation, value));
        return this;
    }

    public final   <T extends Comparable<T>> CustomerSpecificationBuilder with(SearchCriteria<T> searchCriteria) {
        params.add(searchCriteria);
        return this;
    }

    public Specification<Customer> build() {
        if (params.isEmpty()) {
            return null;
        }

        Specification<Customer> result = new CustomerSpecification(new CustomerSearchDto(List.of(params.get(0)), null));
        for (int idx = 1; idx < params.size(); idx++) {
            SearchCriteria<?> criteria = params.get(idx);
            result = SearchOperation.getDataOption(criteria.getDataOption()) == SearchOperation.ALL
                    ? Specification.where(result).and(new CustomerSpecification(new CustomerSearchDto(List.of(criteria), null)))
                    : Specification.where(result).or(new CustomerSpecification(new CustomerSearchDto(List.of(criteria), null)));
        }
        return result;
    }
}

