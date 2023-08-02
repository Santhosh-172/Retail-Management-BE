package com.retail.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.main.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	Customer findByphoneNumber(String phoneNumber);

}
