package com.reznikov.smartenergycustomer.events;



import com.reznikov.smartenergycustomer.dto.CustomerFullDto;
import com.reznikov.smartenergycustomer.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;


@EnableBinding(Sink.class)
public class KafkaConsumerService {
    @Autowired
    CustomerService customerService;


    @StreamListener(Sink.INPUT)
    public void consumeDressesTopic(CustomerFullDto customer) {
        System.out.println("Received message from 'dresses' topic: " + customer);
        customerService.updateCustomer(customer);
    }
}
