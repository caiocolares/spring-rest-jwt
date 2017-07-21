package com.opustech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opustech.model.Feature;
import com.opustech.model.Product;

public interface FeatureRepository extends JpaRepository<Feature, Integer> {
	
	List<Feature> findByProductAndName(Product product, String name);

}
