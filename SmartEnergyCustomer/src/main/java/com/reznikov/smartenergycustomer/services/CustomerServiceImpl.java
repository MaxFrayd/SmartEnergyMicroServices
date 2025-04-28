package com.reznikov.smartenergycustomer.services;

import com.reznikov.smartenergycustomer.domains.Address;
import com.reznikov.smartenergycustomer.domains.Customer;
import com.reznikov.smartenergycustomer.dto.CustomerFullDto;
import com.reznikov.smartenergycustomer.dto.CustomerRegDto;
import com.reznikov.smartenergycustomer.enums.CustomerStatus;
import com.reznikov.smartenergycustomer.repositories.AddressRepository;
import com.reznikov.smartenergycustomer.repositories.CustomerRepository;
import com.reznikov.smartenergycustomer.repositories.SupplierRedisRepository;
import com.reznikov.smartenergycustomer.utils.DuplicateEntityException;
import com.reznikov.smartenergycustomer.utils.InvalidFormatException;
import com.reznikov.smartenergycustomer.utils.ModelMapper;
import com.reznikov.smartenergycustomer.utils.SupplierNotFoundException;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CustomerServiceImpl implements CustomerService {
    @Resource
    private CustomerRepository customerRepository;
    @Resource
    private AddressRepository addressRepository;

    @Resource
    private ModelMapper modelMapper;



    @Override
    public List<CustomerRegDto> findSuppliersByCriteria(String name, String email) {
        List<Customer> customers = customerRepository.findAllByNameAndEmail(name, email);
        return customers.stream()
                .map(modelMapper::toFormDto)
                .collect(Collectors.toList());
    }

    public String addCustomer(@Valid CustomerRegDto customerRegDto) {

        if (customerRepository.findByEmail(customerRegDto.getEmail()) != null){
            throw new DuplicateEntityException("Customer with such email is already exists");
        }

        // Check if longitude and latitude strings are valid numbers
        if (!isValidLatitude(customerRegDto.getAddress().getLatitude()) || !isValidLongitude(customerRegDto.getAddress().getLongitude())) {
            throw new InvalidFormatException("Longitude and Latitude must be valid numbers");
        }
        Customer customer =  modelMapper.fromFormDto(customerRegDto);
        Address address =  addressRepository.save(customer.getAddress());
        customer.setAddress(address);
        customerRepository.save(customer);
        return "Supplier added";
    }
    private boolean isValidLatitude(Double latitude) {
        return latitude != null && latitude >= -90 && latitude <= 90;
    }

    private boolean isValidLongitude(Double longitude) {
        return longitude != null && longitude >= -180 && longitude <= 180;
    }

    public void updateCustomer(CustomerFullDto customer) {
         customerRepository.save(modelMapper.fromFullDto(customer));
    }

    public List<Customer> getAllSuppliers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getSupplierById(Long id) {
        return customerRepository.findById(id);
    }

    public void deleteSupplier(Long id) {
        customerRepository.deleteById(id);
    }


    @Override
    public String activateSupplier(Long id) {
        Customer customer =  this.getSupplierById(id).orElseThrow(()->new SupplierNotFoundException("Supplier is not found "));
        customer.setStatus(CustomerStatus.ACTIVE);
        customerRepository.save(customer);
        return "Activated";
    }

    @Override
    public Page<Customer> findBySearchCriteria(Specification<Customer> spec, Pageable page) {

        return customerRepository.findAll(spec, page);


    }

    @Override
    public List<CustomerFullDto> getCustomersBySupplier(Long supplierId) {

        return customerRepository.findAllBySupplierId(supplierId)
                .stream()
                .map(cs-> modelMapper.toFullDto(cs)).collect(Collectors.toList());
    }
}
