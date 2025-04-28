package com.reznikov.smartenergycustomer.utils;

import com.github.javafaker.Faker;
import com.reznikov.smartenergycustomer.domains.Address;
import com.reznikov.smartenergycustomer.domains.Customer;
import com.reznikov.smartenergycustomer.enums.CustomerStatus;
import com.reznikov.smartenergycustomer.repositories.AddressRepository;
import com.reznikov.smartenergycustomer.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataInitializer implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    private final Faker faker = new Faker();

    @Override
    public void run(String... args) {
        for (int i = 1; i <= 20; i++) {
            Address address = new Address();
            address.setLatitude(Double.parseDouble(faker.address()
                    .latitude()
                    .replace("-", "")
                    .replace(",", ".")
            ));
            address.setLongitude(Double.parseDouble(faker.address()
                    .longitude()
                    .replace("-","")
                    .replace(",", ".")
            ));
            address.setCity(faker.address().city());
            addressRepository.save(address);

            Customer customer = new Customer();
            customer.setName(faker.company().name());
            customer.setEmail(faker.internet().emailAddress());
            customer.setAddress(address);
            customer.setEnergyAmount(faker.number().randomDouble(2, 1000, 5000));
            customer.setStatus(CustomerStatus.ACTIVE);
            customer.setPricePrKwt(faker.number().randomDouble(2, 1000, 5000));
            customer.setSupplierId((long) faker.number().numberBetween(1,3));
            customerRepository.save(customer);
        }
    }
}