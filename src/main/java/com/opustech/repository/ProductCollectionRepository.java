package com.opustech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opustech.model.Enterprise;
import com.opustech.model.ProductCollection;

public interface ProductCollectionRepository extends JpaRepository<ProductCollection, Integer> {

	List<ProductCollection> findByEnterprise(Enterprise enterprise);
	
}
