package com.reznikov.smartenergy.specifications;

import com.reznikov.smartenergy.domains.Supplier;
import com.reznikov.smartenergy.dto.SupplierSearchDto;
import com.reznikov.smartenergy.enums.SearchOperation;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SupplierSpecificationBuilder {

    private final List<SearchCriteria<?>> params;

    public SupplierSpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public final <T extends Comparable<T>> SupplierSpecificationBuilder with(String key, String operation, T value) {
        params.add(new SearchCriteria<>(key, operation, value));
        return this;
    }

    public final   <T extends Comparable<T>> SupplierSpecificationBuilder with(SearchCriteria<T> searchCriteria) {
        params.add(searchCriteria);
        return this;
    }

    public Specification<Supplier> build() {
        if (params.isEmpty()) {
            return null;
        }

        Specification<Supplier> result = new SupplierSpecification(new SupplierSearchDto(List.of(params.get(0)), null));
        for (int idx = 1; idx < params.size(); idx++) {
            SearchCriteria<?> criteria = params.get(idx);
            result = SearchOperation.getDataOption(criteria.getDataOption()) == SearchOperation.ALL
                    ? Specification.where(result).and(new SupplierSpecification(new SupplierSearchDto(List.of(criteria), null)))
                    : Specification.where(result).or(new SupplierSpecification(new SupplierSearchDto(List.of(criteria), null)));
        }
        return result;
    }
}

