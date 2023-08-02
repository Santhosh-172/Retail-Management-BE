package com.retail.main.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.retail.main.exception.ResourceNotFoundException;
import com.retail.main.model.Billing;
import com.retail.main.model.Product;
import com.retail.main.model.Stock;
import com.retail.main.repository.StockRepository;

@Service
public class StockService {

	@Autowired
	StockRepository stockRepository;

	public Stock insertOrUpdate(Stock stock) {
		int productId = stock.getProduct().getId();
		System.out.println(productId);
		Optional<Stock> existingStock = stockRepository.findByProductId(productId);

		if (existingStock.isPresent()) {
			// If stock entry already exists, update the quantity by adding the new quantity
			int newQuantity = existingStock.get().getQuantity() + stock.getQuantity();
			
			
			existingStock.get().setQuantity(newQuantity);
			existingStock.get().setDateOfSupply(LocalDate.now());
			return stockRepository.save(existingStock.get());
		} else {
			// If stock entry does not exist, create a new stock entry
			stock.setDateOfSupply(LocalDate.now());
			return stockRepository.save(stock);
		}
	}
	
	public Stock update(Stock stock) {
		return stockRepository.save(stock);
	}

	public List<Stock> getAll() {

		return stockRepository.findAll();
	}

	public Stock getById(int id) {
		// TODO Auto-generated method stub
		Optional<Stock> optional = stockRepository.findById(id);
		if (!optional.isPresent()) {
			return null;
		}
		return optional.get();
	}

	public void updateStockQuantity(Product product, int quantityChange) throws ResourceNotFoundException {
	    Stock stock = stockRepository.findByProduct(product);
	    if (stock != null) {
	        int currentQuantity = stock.getQuantity();
	        int newQuantity = currentQuantity + quantityChange;
	        if (newQuantity < 0) {
				throw new ResourceNotFoundException("Insufficient Stock for product:  " + stock.getProduct().getTitle());
			}
	        stock.setQuantity(newQuantity);
	        stockRepository.save(stock);
	    } 
	}

	public List<Stock> filterStocks(String product) {
		
		List<Stock> allStocks = stockRepository.findAll();
		
    List<Stock> filteredStock = allStocks.stream()
	            .filter(stock -> (stock == null || stock.getProduct().getTitle().toLowerCase().startsWith(product.toLowerCase())))
	            .collect(Collectors.toList());
	                
	            

	        return filteredStock;
	}
	
	


}
