package com.reznikov.smartenergy.specifications;

import com.reznikov.smartenergy.domains.Supplier;
import com.reznikov.smartenergy.dto.SupplierSearchDto;
import com.reznikov.smartenergy.enums.SearchOperation;
import javax.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SupplierSpecification implements Specification<Supplier> {

    private final SupplierSearchDto criteria;

    public SupplierSpecification(SupplierSearchDto criteria) {
        this.criteria = criteria;
    }
    private <T extends Comparable<T>> Predicate getPredicate(Root<Supplier> root, CriteriaBuilder builder,SearchCriteria<T> searchCriteria  ){
        String key = searchCriteria.getKey();
        SearchOperation operation = SearchOperation.getSimpleOperation(searchCriteria.getOperation());
        T value = searchCriteria.getValue();//DataType of Value ?
        Predicate predicate = null;

        if (operation != null) {
            switch (operation) {
                case CONTAINS:
                   predicate=builder.like(root.get(key), "%" + value + "%");
                    break;
                case DOES_NOT_CONTAIN:
                   predicate=builder.notLike(root.get(key), "%" + value + "%");
                    break;
                case EQUAL:
                   predicate=builder.equal(root.get(key), value);
                    break;
                case NOT_EQUAL:
                   predicate=builder.notEqual(root.get(key), value);
                    break;
                case GREATER_THAN:      //Object<Comparable>
                   predicate=builder.greaterThan(root.get(key), value);
                    break;
                case GREATER_THAN_EQUAL:
                   predicate=builder.greaterThanOrEqualTo(root.get(key), value);
                    break;
                case LESS_THAN:
                   predicate=builder.lessThan(root.get(key), value);
                    break;
                case LESS_THAN_EQUAL:
                   predicate=builder.lessThanOrEqualTo(root.get(key), value);
                    break;
                case NUL:
                   predicate=builder.isNull(root.get(key));
                    break;
                case NOT_NULL:
                   predicate=builder.isNotNull(root.get(key));
                    break;
                // Add other cases as necessary
            }
        }

        return predicate;

    }

    @Override

    public Predicate toPredicate(@NonNull Root<Supplier> root,@NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        for (SearchCriteria<?> searchCriteria : criteria.getSearchCriteriaList()) {
            predicates.add(getPredicate(root,builder,searchCriteria));
        }

        if (criteria.getDataOption() != null) {
            SearchOperation dataOptionOperation = SearchOperation.getDataOption(criteria.getDataOption());
            if (dataOptionOperation != null) {
                switch (dataOptionOperation) {
                    case ALL:
                        return builder.and(predicates.toArray(new Predicate[0]));
                    case ANY:
                        return builder.or(predicates.toArray(new Predicate[0]));
                }
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}

