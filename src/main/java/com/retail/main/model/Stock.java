package com.retail.main.model;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	private Product product;

	private int quantity;

	private LocalDate dateOfSupply;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public LocalDate getDateOfSupply() {
		return dateOfSupply;
	}

	public void setDateOfSupply(LocalDate dateOfSupply) {
		this.dateOfSupply = dateOfSupply;
	}

	@Override
	public String toString() {
		return "Stock [id=" + id + ", product=" + product + ", quantity=" + quantity + ", dateOfSupply=" + dateOfSupply
				+ "]";
	}

	
	}
	
	

