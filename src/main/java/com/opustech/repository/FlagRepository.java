package com.opustech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opustech.model.Enterprise;
import com.opustech.model.Flag;

public interface FlagRepository extends JpaRepository<Flag, Integer> {

	List<Flag> findByEnterprise(Enterprise enterprise);
	
}
