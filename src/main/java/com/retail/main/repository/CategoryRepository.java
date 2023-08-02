package com.retail.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.main.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
