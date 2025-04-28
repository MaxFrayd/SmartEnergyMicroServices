package com.reznikov.smartenergy.utils;

public class SupplierNotFoundException extends  RuntimeException {
    public SupplierNotFoundException(String message) {
        super(message);
    }
}
