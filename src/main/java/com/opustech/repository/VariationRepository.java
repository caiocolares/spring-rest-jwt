package com.opustech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opustech.model.Variation;

public interface VariationRepository extends JpaRepository<Variation, Integer> {

}
