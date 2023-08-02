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

import com.retail.main.exception.ResourceNotFoundException;
import com.retail.main.model.Category;
import com.retail.main.model.Product;
import com.retail.main.model.Stock;
import com.retail.main.repository.StockRepository;
import com.retail.main.service.CategoryService;
import com.retail.main.service.ProductService;

@RestController
@RequestMapping("/inventory-service/product")
public class ProductController {

	@Autowired
	private ProductService productService; // injecting Service in Controller : DI(Dependency Injection)

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private StockRepository stockRepository;

	@PostMapping("/add/{categoryId}")
	public ResponseEntity<?> postProduct(@RequestBody Product product, @PathVariable("categoryId") int catId) {

		Category category = categoryService.getById(catId);
		if (category == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Category ID given");

		product.setCategory(category);

		product = productService.insert(product);
		return ResponseEntity.status(HttpStatus.OK).body(product);
	}

	/*
	 * PATH: /product/all Method: GET RequestBody: None response: List<Product>
	 * PathVariable: None
	 */
	@GetMapping("/all")
	public List<Product> getAllProducts() {
		List<Product> list = productService.getAllProducts();
		return list;
	}

	/*
	 * PATH: /product/one Method: GET RequestBody: None response: Product
	 * PathVariable: ID
	 */
	@GetMapping("/one/{id}") // this id is called as path variable
	public ResponseEntity<?> getProduct(@PathVariable("id") int id) {
		Product product = productService.getproduct(id);
		if (product == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID given");
		}
		return ResponseEntity.status(HttpStatus.OK).body(product);
	}

	// Not a professional way
	@GetMapping("/one/alternate/{id}")
	public Object getProductAlternate(@PathVariable("id") int id) {
		try {
			Product product = productService.getproductAlternate(id);
			return product;
		} catch (ResourceNotFoundException e) {
			return e.getMessage();
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable("id") int id, @RequestBody Product newProduct) {
		// Step 0 : validation for request body: newProduct
		if (newProduct.getTitle() == null || !newProduct.getTitle().trim().matches("[a-zA-Z0-9- *]+"))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title has to have valid format [a-zA-Z0-9- ]");

		if (newProduct.getDescription() == null || newProduct.getDescription().equals(""))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Description cannot be nullor blank");

		if (newProduct.getPrice() == 0)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Price must have a value other than 0");

		// Step 1: Validate the id given
		Product oldProduct = productService.getproduct(id);
		if (oldProduct == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID given");
		}
		/*
		 * 2 techniques {old has id whereas new does not have id} 1. Transfer new values
		 * to old(that has id) 2. Transfer id from old to new.
		 */
		
		Category category =  categoryService.getById(newProduct.getCategory().getId()) ;

		newProduct.setId(oldProduct.getId());
		newProduct.setCategory(category);

		newProduct = productService.insert(newProduct);
		return ResponseEntity.status(HttpStatus.OK).body(newProduct);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") int id) {
		// Step 1: validate id
		Product product = productService.getproduct(id);
		Stock stock = stockRepository.findByProduct(product);
		if (product == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID given");
		}
		
		if( stock != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( product.getTitle()+ " has Stock");
		}

		productService.deleteProduct(product);

		return ResponseEntity.status(HttpStatus.OK).body("Product deleted..");

	}

	@GetMapping("category/{name}")
	public List<Product> byCategory(@PathVariable("name") String name) throws ResourceNotFoundException {

		List<Product> product = productService.loadProductByCategory(name);

		return product;
	}
	
	 @GetMapping("/search/{name}")
	    public ResponseEntity<List<Product>> searchProductsByName(@PathVariable String name) {
	        List<Product> products = productService.searchProductsByName(name);
	        if (products.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }
	        return ResponseEntity.status(HttpStatus.OK).body(products);
	    }
}
