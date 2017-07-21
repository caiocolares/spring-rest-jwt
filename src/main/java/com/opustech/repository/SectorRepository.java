package com.opustech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opustech.model.Enterprise;
import com.opustech.model.Sector;

public interface SectorRepository extends JpaRepository<Sector, Integer> {

	List<Sector> findByEnterprise(Enterprise enterprise);
}
