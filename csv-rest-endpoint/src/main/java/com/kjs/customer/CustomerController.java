package com.kjs.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
    	List<CustomerDto> customers = new ArrayList<CustomerDto>();
    	
    	this.customerRepository.findAll().forEach(customer -> {
    		customers.add(convert(customer));
        });
    	
    	return ResponseEntity.ok(customers);
    }
    
    @GetMapping(value = "/{customerRef}", produces = "application/json")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customerRef") String customerRef) {
    	Optional<CustomerBean> entity = this.customerRepository.findByReference(customerRef);
    	
    	return entity.isPresent() ? 
    			ResponseEntity.ok(convert(entity.get())) :
    			ResponseEntity.badRequest().build();
    }
    
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveCustomer(@RequestBody CustomerDto customer) {
    	this.customerRepository.save(convert(customer));
    	return ResponseEntity.ok().build();
    }
    
    private CustomerBean convert(CustomerDto customer) {
    	return new CustomerBean()
    			.setReference(customer.getReference())
    			.setName(customer.getName())
    			.setAddressOne(customer.getAddressOne())
    			.setAddressTwo(customer.getAddressTwo())
    			.setTown(customer.getTown())
    			.setCounty(customer.getCounty())
    			.setCountry(customer.getCountry())
    			.setPostcode(customer.getPostcode())
    			;
    }
    
    private CustomerDto convert(CustomerBean customer) {
    	return new CustomerDto()
    			.setReference(customer.getReference())
    			.setName(customer.getName())
    			.setAddressOne(customer.getAddressOne())
    			.setAddressTwo(customer.getAddressTwo())
    			.setTown(customer.getTown())
    			.setCounty(customer.getCounty())
    			.setCountry(customer.getCountry())
    			.setPostcode(customer.getPostcode())
    			;
    }
}
