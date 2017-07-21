package com.opustech.controller;

import java.io.Serializable;

import org.springframework.http.ResponseEntity;

public interface OpusController<T> {

	default ResponseEntity<T> mountResponse(String route,T entity){
		return null;
	}
		
	public ResponseEntity<?> findById(Serializable id);
	public ResponseEntity<?> findAll();
	public ResponseEntity<?> save(T entity);
	
}
