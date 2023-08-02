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
import org.springframework.web.bind.annotation.RestController;

import com.retail.main.model.Category;
import com.retail.main.service.CategoryService;


@RestController
@RequestMapping("/inventory-service/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/add")
	public Category postSupplier(@RequestBody Category category) {
		return categoryService.insert(category);
	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<?> updateCustomer(@RequestBody Category category, @PathVariable("id") int id) {
		
		
		Category oldCategory  = categoryService.getById(id);
		if(oldCategory == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Invalid ID given");
		}
		/* 2 techniques {old has id whereas new does not have id}
		 * 1. Transfer new values to old(that has id)
		 * 2. Transfer id from old to new.  
		 */
		category.setId(oldCategory.getId());
		category = categoryService.insert(category);
	    return ResponseEntity.status(HttpStatus.OK)
				.body(category);
	}
	
	@GetMapping("/all")
	public List<Category> getAll() {
		return categoryService.getAll();
	}
	
	@GetMapping("/one/{id}") //this id is called as path variable
	public ResponseEntity<?> getProduct(@PathVariable("id") int id) {
		Category category  = categoryService.getById(id);
		if(category == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Invalid ID given");
		}
		return ResponseEntity.status(HttpStatus.OK).body(category); 
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		
			Category category = categoryService.getById(id);
			if(category == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Invalid Id");
		
		
			categoryService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).build();
}
	
}
