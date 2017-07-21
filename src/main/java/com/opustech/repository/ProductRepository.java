package com.opustech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opustech.model.Product;
import com.opustech.model.ProductCollection;
import com.opustech.model.ProductId;

public interface ProductRepository extends JpaRepository<Product, ProductId> {

	List<Product> findByCollection(ProductCollection collection);
	
	List<Product> findByEnteprise(Integer enterprise);
	
}
