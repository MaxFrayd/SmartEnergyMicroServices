package com.reznikov.smartenergy.dto;

import com.reznikov.smartenergy.specifications.SearchCriteria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class SupplierSearchDto  implements Serializable {
    private List<SearchCriteria<?>> searchCriteriaList ;
    private  String dataOption;
}
