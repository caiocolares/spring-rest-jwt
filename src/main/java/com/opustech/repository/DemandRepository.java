package com.opustech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opustech.model.Demand;
import com.opustech.model.Enterprise;
import com.opustech.model.ProductCollection;

public interface DemandRepository extends JpaRepository<Demand, Integer> {

	List<Demand> findByEnterprise(Enterprise enterprise);
	List<Demand> findByEnterpriseAndCollection(Enterprise enterprise, ProductCollection collection);
	
}
