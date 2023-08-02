package com.retail.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.retail.main.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	
//all the methods of JpaRepository are now available in ProductRepository
	@Query("select p from Product p where p.category.name=?1")
	List<Product>
	 getProductByCategory(String categoryname);
	
	List<Product> findByTitleContainingIgnoreCase(String name);
	
	/* Notable Methods: 
	 * save(T):T : saves the object in DB 
	 * getAll(): List<T> : returns all the objects present in the DB
	 * findById(id): Optional<T> : Returns the Object on the basis of ID. 
	 * delete(id) : void : deletes the object based on id. 
	 * 
	 * 
	 */
	
	
	
	
}
