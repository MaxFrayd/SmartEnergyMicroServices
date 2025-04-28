package com.reznikov.smartenergycustomer.api;

import com.reznikov.smartenergycustomer.domains.Customer;
import com.reznikov.smartenergycustomer.dto.CustomerFullDto;
import com.reznikov.smartenergycustomer.dto.CustomerRegDto;
import com.reznikov.smartenergycustomer.dto.CustomerSearchDto;
import com.reznikov.smartenergycustomer.services.CustomerService;
import com.reznikov.smartenergycustomer.specifications.CustomerSpecificationBuilder;
import com.reznikov.smartenergycustomer.specifications.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
@Validated


public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/search")
    public ResponseEntity<List<CustomerRegDto>> findCustomerByComplexCriterias(
            @RequestParam String name,
            @RequestParam String email) {
        List<CustomerRegDto> supplierDtos = customerService.findSuppliersByCriteria(name, email);
        return ResponseEntity.ok(supplierDtos);
    }


    @PostMapping
    public ResponseEntity<String> addCustomer(@RequestBody @Validated CustomerRegDto customerDto) {
        return ResponseEntity.ok(customerService.addCustomer(customerDto));
    }

    @GetMapping("{id}/activate")
    public ResponseEntity<String> activateCustomer(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.activateSupplier(id));
    }


    @PutMapping
    public ResponseEntity<String> updateCustomer(@RequestBody CustomerFullDto customer) {
        customerService.updateCustomer(customer);
        return ResponseEntity
                .ok()
                .header("Content-Type", "text/plain")
                .body("Customer updated successfully");
    }

    @GetMapping
        public ResponseEntity<List<Customer>> getAllCustomers() {
            return ResponseEntity.ok(customerService.getAllSuppliers());
    }
//    @RolesAllowed({"ADMIN"})
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Customer>> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getSupplierById(id));
    }
//    @RolesAllowed({"ADMIN"})
    @GetMapping("/supplier/{id}")
    public ResponseEntity<List<CustomerFullDto>> getCustomersBySupplierId(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomersBySupplier(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search/byCriteria")
    ResponseEntity<Page<Customer>> searchByCriteria(
            @RequestParam(name = "pageNum",
                    defaultValue = "0") int pageNum,
            @RequestParam(name = "pageSize",
                    defaultValue = "10") int pageSize,
            @RequestBody CustomerSearchDto
                    customerSearchDto) {

        CustomerSpecificationBuilder builder = new CustomerSpecificationBuilder();

        List<SearchCriteria<?>> criteriaList = customerSearchDto.getSearchCriteriaList();

        if(criteriaList != null){
            for (SearchCriteria<?> searchCriteria : criteriaList) {
                searchCriteria.setDataOption(customerSearchDto.getDataOption());
                builder.with(searchCriteria);
            }
        }
        Pageable page = PageRequest.of(pageNum, pageSize,
                Sort.by("energyAmount")
                        .ascending());


        Page<Customer> suppliers = customerService.findBySearchCriteria(builder.build(), page);

        return ResponseEntity.ok(suppliers);
    }
}