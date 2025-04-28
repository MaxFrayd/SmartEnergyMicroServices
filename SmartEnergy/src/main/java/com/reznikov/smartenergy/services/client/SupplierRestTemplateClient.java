package com.reznikov.smartenergy.services.client;


import com.reznikov.smartenergy.dto.CustomerFullDto;
//import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@Component
public class SupplierRestTemplateClient {

//    @Resource
//    private KeycloakRestTemplate restTemplate;
    @Resource
    private RestTemplate restTemplate;

    public List<CustomerFullDto> getCustomers(Long organizationId) {
        ResponseEntity<List<CustomerFullDto>> restExchange =
                restTemplate.exchange(
                        "http://gateway:8072/smartenergycustomer/api/customer/supplier/{id}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {},
                        organizationId);

        return restExchange.getBody();
    }

    public void updateCustomer(CustomerFullDto customer) {
        restTemplate.put(
                "http://gateway:8072/smartenergycustomer/api/customer", customer
        );
    }

}
