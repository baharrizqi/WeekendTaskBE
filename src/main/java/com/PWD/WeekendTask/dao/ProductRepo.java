package com.PWD.WeekendTask.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PWD.WeekendTask.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {
		public Product findByProductName(String productName);
}
