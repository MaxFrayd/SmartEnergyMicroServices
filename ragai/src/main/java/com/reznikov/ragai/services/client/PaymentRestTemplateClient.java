package com.reznikov.ragai.services.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Component
public class PaymentRestTemplateClient {

    @Resource
    private RestTemplate restTemplate;

    public byte[] getPayments() {
        ResponseEntity<byte[]> restExchange =
                restTemplate.exchange(
                        "http://gateway:8072/paymentservice/api/payments/csv",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});

        return restExchange.getBody();
    }
}

