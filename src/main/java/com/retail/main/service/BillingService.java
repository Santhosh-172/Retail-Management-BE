package com.retail.main.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retail.main.model.Billing;
import com.retail.main.model.Customer;
import com.retail.main.model.Product;
import com.retail.main.repository.BillingRepository;

@Service
public class BillingService {
	
	@Autowired
	private BillingRepository billingRepository;
	
	@Autowired
	private CustomerService customerService;
	
	

	public Billing insert(Billing billing) {
		// TODO Auto-generated method stub
		return billingRepository.save(billing);
	}



	public List<Billing> getAll() {
		// TODO Auto-generated method stub
		return billingRepository.findAll();
	}
	
	public List<Double> calculateProductPrices(List<Product> products, List<Integer> productQuantities) {
        List<Double> productPrices = new ArrayList<>();
        Map<Integer, Integer> productQuantityMap = new HashMap<>();
        for (int i = 0; i < products.size(); i++) {
            productQuantityMap.put(products.get(i).getId(), productQuantities.get(i));
        }
        for (Product product : products) {
            int quantity = productQuantityMap.getOrDefault(product.getId(), 0);
            double productPrice = product.getPrice() * quantity;
            productPrices.add(productPrice);
        }
        return productPrices;
    }

    public double calculateNetPrice(List<Product> products, List<Integer> productQuantities) {
        double netPrice = 0;
        Map<Integer, Integer> productQuantityMap = new HashMap<>();
        for (int i = 0; i < products.size(); i++) {
            productQuantityMap.put(products.get(i).getId(), productQuantities.get(i));
        }
        for (Product product : products) {
            int quantity = productQuantityMap.getOrDefault(product.getId(), 0);
            double productPrice = product.getPrice() * quantity;
            netPrice += productPrice;
        }
        return netPrice;
    }
    
    public void updateCustomerLoyaltyPoints(Customer customer, double netPrice) {
    	double loyaltyPointsToAdd = netPrice / 100.0;
        double currentLoyaltyPoints = customer.getLoyaltyPoints();
        System.out.println("Added points"+currentLoyaltyPoints);
        customer.setLoyaltyPoints(currentLoyaltyPoints + loyaltyPointsToAdd);
        customerService.insert(customer); // Save the updated customer entity to the database
    }



	public void updateLoyaltyPlusNetPrice(Customer customer, double netPrice, Billing billing) {
		// TODO Auto-generated method stub
		
        double currentLoyaltyPoints = customer.getLoyaltyPoints();
        System.out.println("current"+currentLoyaltyPoints);
        double redeemPoints = billing.getRedeemPoints();
        
        double remainingPoints = currentLoyaltyPoints - redeemPoints;
        
        if(remainingPoints < 0) {
        	remainingPoints = 0;
        }
        
        
        netPrice = netPrice - redeemPoints;
        
        if(netPrice < 0) {
        	netPrice = 0;
        }
        
        customer.setLoyaltyPoints(remainingPoints);
        System.out.println("Remaining Points"+customer.getLoyaltyPoints());
        customerService.insert(customer);
        
        billing.setNetPrice(netPrice);
	}

	 public List<Billing> filterBillings(String date, String customerName, Double netPrice) {
	        // Implement the filtering logic here based on the provided criteria
	        // You can use the billingRepository to fetch the billings and then filter them based on the criteria
	        List<Billing> allBillings = billingRepository.findAll(); // Fetch all billings from the database

	        // Example filtering logic based on date, customer, and netPrice
	        List<Billing> filteredBillings = allBillings.stream()
	            .filter(billing -> (date == null || billing.getDate().equals(LocalDate.parse(date)))
	                && (customerName == null || billing.getCustomer().getName().toLowerCase().contains(customerName.toLowerCase()))
	                && (netPrice == null || billing.getNetPrice() <= netPrice))
	            .collect(Collectors.toList());

	        return filteredBillings;
	    }
	
}
