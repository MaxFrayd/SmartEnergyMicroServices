package com.reznikov.smartenergycustomer.utils;

import  org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ManagerBeans {
    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }
}
