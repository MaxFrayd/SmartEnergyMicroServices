package com.reznikov.smartenergy.services;

import com.reznikov.smartenergy.domains.Address;
import com.reznikov.smartenergy.domains.Supplier;
import com.reznikov.smartenergy.dto.CustomerFullDto;
import com.reznikov.smartenergy.dto.EnergyUpdateDto;
import com.reznikov.smartenergy.dto.SupplierFullDto;
import com.reznikov.smartenergy.dto.SupplierRegDto;
import com.reznikov.smartenergy.enums.SupplierStatus;
import com.reznikov.smartenergy.events.model.SupplierEnergyChangeModel;
import com.reznikov.smartenergy.events.source.SimpleSourceBean;
import com.reznikov.smartenergy.repositories.AddressRepository;
import com.reznikov.smartenergy.repositories.SupplierRepository;
import com.reznikov.smartenergy.services.client.SupplierRestTemplateClient;
import com.reznikov.smartenergy.utils.DuplicateEntityException;
import com.reznikov.smartenergy.utils.InvalidFormatException;
import com.reznikov.smartenergy.utils.ModelMapper;
import com.reznikov.smartenergy.utils.SupplierNotFoundException;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SupplierServiceImpl implements SupplierService {
    @Resource
    private SupplierRepository supplierRepository;
    @Resource
    private AddressRepository addressRepository;

    @Resource
    private ModelMapper modelMapper;

    @Autowired
    private SupplierRestTemplateClient supplierRestTemplateClient;
    @Resource
    private SimpleSourceBean simpleSourceBean;

    @Override
    public List<SupplierRegDto> findSuppliersByCriteria(String name, String email) {
        List<Supplier> suppliers = supplierRepository.findAllByNameAndEmail(name, email);
        return suppliers.stream()
                .map(modelMapper::toFormDto)
                .collect(Collectors.toList());
    }

    public String addSupplier(@Valid SupplierRegDto supplierDto) {

        if (supplierRepository.findByEmail(supplierDto.getEmail()) != null){
            throw new DuplicateEntityException("Supplier with such email is already exists");
        }

        // Check if longitude and latitude strings are valid numbers
        if (!isValidLatitude(supplierDto.getAddress().getLatitude()) || !isValidLongitude(supplierDto.getAddress().getLongitude())) {
            throw new InvalidFormatException("Longitude and Latitude must be valid numbers");
        }
        Supplier supplier =  modelMapper.fromFormDto(supplierDto);
        Address address =  addressRepository.save(supplier.getAddress());
        supplier.setAddress(address);
        supplierRepository.save(supplier);
        return "Supplier added";
    }
    private boolean isValidLatitude(Double latitude) {
        return latitude != null && latitude >= -90 && latitude <= 90;
    }

    private boolean isValidLongitude(Double longitude) {
        return longitude != null && longitude >= -180 && longitude <= 180;
    }
    @Override
    public void updateSupplier(SupplierFullDto supplierFullDto) {
        Supplier existingSupplier = supplierRepository.findById(supplierFullDto.getId()).orElseThrow(
                () -> new EntityNotFoundException("Supplier not found for ID: " + supplierFullDto.getId()));
         supplierRepository.save(modelMapper.updateSupplierFromDto(supplierFullDto, existingSupplier));
    }
    @Override
    public void updateSupplierEnergyAmount(EnergyUpdateDto energyUpdateDto) {
        SupplierEnergyChangeModel changeModel = modelMapper.toEnergyChangeModel(energyUpdateDto.getSupplierFullDto(), energyUpdateDto.getUpdatedEnergy());

        simpleSourceBean.publishSupplierEnergyUpdate(changeModel);

    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public SupplierFullDto getSupplierById(Long id) {

        return modelMapper.toFullDto(supplierRepository.findById(id).orElseThrow( () -> new EntityNotFoundException("Supplier not found for ID: " + id)));
    }

    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }


    @Override
    public String activateSupplier(Long id) {
        Supplier supplier =  supplierRepository.findById(id).orElseThrow(()->new SupplierNotFoundException("Supplier is not found "));
        supplier.setStatus(SupplierStatus.ACTIVE);
        supplierRepository.save(supplier);
        return "Activated";
    }

    @Override
    public Page<Supplier> findBySearchCriteria(Specification<Supplier> spec, Pageable page) {
        return supplierRepository.findAll(spec, page);
    }

    @Override
    public void releaseSupplierEnergy(Long sid, Long cid) {
        //Retrieve all customers for the supplier
        List<CustomerFullDto> customers = supplierRestTemplateClient.getCustomers(sid);

        //Find customer to be removed
        CustomerFullDto requestedCustomer = customers.stream().filter(cust-> cust.getId().equals(cid))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        //Get the energy amount of the customer to remove
        Double requestedCustomerEnergy  = requestedCustomer.getEnergyAmount();

        //Fetch the supplier details
        Supplier supplier =  supplierRepository.findById(sid)
                .orElseThrow(()->new RuntimeException("Supplier not found"));

        supplier.increaseEnergyAmount(requestedCustomerEnergy);

        //Update the supplier in database
        supplierRepository.save(supplier);

        //Set the customer's supplierId to null;
        requestedCustomer.setSupplierId(null);

        // Update the customer (assuming the service has a method for that)
        //supplierRestTemplateClient.updateCustomer(requestedCustomer);
        simpleSourceBean.publishCustomerChange(requestedCustomer);
    }

    @Override
    public List<CustomerFullDto> getCustomersBySupplierId(Long sid) {
        return supplierRestTemplateClient.getCustomers(sid);
    }
}
