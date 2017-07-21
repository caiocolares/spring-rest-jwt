package com.opustech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opustech.model.Enterprise;
import com.opustech.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);
	
	List<User> findByEnterprise(Enterprise enterprise);
	
}
