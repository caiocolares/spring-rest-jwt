package com.opustech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opustech.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	List<Category> findByEnterpriseIdIn(List<Integer> enterpriseIdList);
	
	List<Category> findByEnterpriseId(Integer entepriseId);
	
}
