package com.retail.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retail.main.model.Category;
import com.retail.main.repository.CategoryRepository;



@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public Category insert(Category category) {
		return categoryRepository.save(category);
	}

	public Category getById(int categoryId) {
		Optional<Category> optional= categoryRepository.findById(categoryId);
		if(!optional.isPresent()) {
			return null; 
		}
		return optional.get();	}

	public List<Category> getAll() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}

	public void delete(int id) {
		// TODO Auto-generated method stub
		categoryRepository.deleteById(id);
		
	}
}
