package com.reznikov.smartenergycustomer.specifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria <T  extends  Comparable<T>> implements Serializable {

    private  String key;
    private  T value;
    private  String operation;
    private  String dataOption;

    public SearchCriteria(String key,String operation, T value) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }
}
