package com.retail.main.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Billing {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;

	@OneToOne
	private Customer customer;

	@ManyToMany
	private List<Product> product;

	@Transient
	private List<Integer> productIds;

	@ElementCollection
	private List<Integer> productQuantities;

	@ElementCollection
	private List<Double> productPrices;

	private double netPrice;

	private double redeemPoints;

	private LocalDate date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Product> getProduct() {
		return product;
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}

	public List<Integer> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<Integer> productIds) {
		this.productIds = productIds;
	}

	public List<Integer> getProductQuantities() {
		return productQuantities;
	}

	public void setProductQuantities(List<Integer> productQuantities) {
		this.productQuantities = productQuantities;
	}

	public List<Double> getProductPrices() {
		return productPrices;
	}

	public void setProductPrices(List<Double> productPrices) {
		this.productPrices = productPrices;
	}

	public double getNetPrice() {
		return netPrice;
	}

	public void setNetPrice(double netPrice) {
		this.netPrice = netPrice;
	}

	public double getRedeemPoints() {
		return redeemPoints;
	}

	public void setRedeemPoints(double redeemPoints) {
		this.redeemPoints = redeemPoints;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
