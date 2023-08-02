package com.retail.main.controller;

import java.time.LocalDate;
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

import com.retail.main.model.Billing;
import com.retail.main.model.Product;
import com.retail.main.model.Stock;
import com.retail.main.service.ProductService;
import com.retail.main.service.StockService;


@RestController
@RequestMapping("/inventory-service/stock")
public class StockController {
	
	@Autowired	
	ProductService productService;
	
	@Autowired
	StockService stockservice;

	@PostMapping("/add/{productId}")
	public ResponseEntity<?> postStock(@RequestBody Stock stock,@PathVariable("productId") int pId){
		
		Product product = productService.getById(pId);
		
		if(product == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Invalid product ID given");
		
		stock.setProduct(product);
		stock.setDateOfSupply(LocalDate.now());
		
		stock = stockservice.insertOrUpdate(stock);
		 return ResponseEntity.status(HttpStatus.OK)
				.body(stock);
	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<?> updateCustomer(@RequestBody Stock stock, @PathVariable("id") int id) {
		
		System.out.println(stock);
		
		Stock oldStock  = stockservice.getById(id);
		if(oldStock == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Invalid ID given");
		}
		/* 2 techniques {old has id whereas new does not have id}
		 * 1. Transfer new values to old(that has id)
		 * 2. Transfer id from old to new.  
		 */
		System.out.println(oldStock);
		stock.setId(oldStock.getId());
		stock.setDateOfSupply(oldStock.getDateOfSupply());
		stock.setProduct(oldStock.getProduct());
		stock = stockservice.update(stock);
	    return ResponseEntity.status(HttpStatus.OK)
				.body(stock);
	}
	
	@GetMapping("/all")
	public List<Stock> getAll() {
		return stockservice.getAll();
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		
		Stock stock = stockservice.getById(id);
			if(stock == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Invalid Id");
		
			Product product = productService.getproduct(id);
			productService.deleteProduct(product);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping("/filter")
    public ResponseEntity<List<Stock>> filterBillings(
        @RequestParam(required = false) String product
        
    ) {
	
        List<Stock> filteredStocks = stockservice.filterStocks(product);
        return ResponseEntity.ok(filteredStocks);
    }
}
