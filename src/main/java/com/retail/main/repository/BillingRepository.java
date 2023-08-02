package com.retail.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.main.model.Billing;

public interface BillingRepository extends JpaRepository<Billing, Integer>{

}
