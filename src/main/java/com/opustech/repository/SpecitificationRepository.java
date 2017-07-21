package com.opustech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opustech.model.Specification;

public interface SpecitificationRepository extends JpaRepository<Specification, Integer> {

	List<Specification> findByEnterpriseId(Integer entepriseId);
	
}
