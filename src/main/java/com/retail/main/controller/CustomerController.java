package com.retail.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retail.main.model.Customer;
import com.retail.main.service.CustomerService;

@RestController
@RequestMapping("/inventory-service/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService; 
	
	@PostMapping("/add")
	public Customer postCustomer(@RequestBody Customer customer) {
		return customerService.insert(customer);
	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @PathVariable("id") int id) {
		
		
		Customer oldCustomer  = customerService.getById(id);
		if(oldCustomer == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Invalid ID given");
		}
		/* 2 techniques {old has id whereas new does not have id}
		 * 1. Transfer new values to old(that has id)
		 * 2. Transfer id from old to new.  
		 */
		customer.setId(oldCustomer.getId());
	    customer = customerService.insert(customer);
	    return ResponseEntity.status(HttpStatus.OK)
				.body(customer);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		
			Customer customer = customerService.getById(id);
			if(customer == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Invalid Id");
		
		
		customerService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body("Customer Deleted");
	}
	
	@GetMapping("/all")
	public List<Customer> getAllCustomer() {
		List<Customer> list =  customerService.getAll();
		return list; 
	}
	
	 @GetMapping("/getByPhoneNumber/{phoneNumber}")
	    public ResponseEntity<?> getCustomerByPhoneNumber(@PathVariable String phoneNumber) {
	        Customer customer = customerService.getByPhoneNumber(phoneNumber);
	        if (customer != null) {
	        	 return ResponseEntity.status(HttpStatus.OK)
	     				.body(customer);
	        } else {
	        	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Invalid Number");
	        }
	    }
	 
	 @GetMapping("/search")
	    public ResponseEntity<Customer> searchCustomerByPhoneNumber(@RequestParam(required = true) String phoneNumber) {
	        Customer customer = customerService.getByPhoneNumber(phoneNumber);
	        if (customer != null) {
	            return ResponseEntity.status(HttpStatus.OK).body(customer);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }
	    }
}
