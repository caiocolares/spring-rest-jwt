package com.opustech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opustech.model.Enterprise;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Integer> {

}
