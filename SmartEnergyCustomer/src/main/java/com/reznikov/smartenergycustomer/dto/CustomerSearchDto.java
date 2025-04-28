package com.reznikov.smartenergycustomer.dto;

import com.reznikov.smartenergycustomer.specifications.SearchCriteria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerSearchDto implements Serializable {
    private List<SearchCriteria<?>> searchCriteriaList ;
    private  String dataOption;
}
