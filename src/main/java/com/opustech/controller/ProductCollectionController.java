package com.opustech.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opustech.model.Enterprise;
import com.opustech.model.ProductCollection;
import com.opustech.repository.ProductCollectionRepository;
import com.opustech.service.AuthorizationService;

@RestController
@RequestMapping("/collection")
@CrossOrigin(origins="*")
public class ProductCollectionController {
	
	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private ProductCollectionRepository collectionRepository;

	
	@GetMapping("/{id}")
	public ResponseEntity<ProductCollection> findById(@PathVariable("id") Integer id) {		
		ProductCollection collection = collectionRepository.findOne(id);		
		return ResponseEntity.ok(collection);	
	}

	@GetMapping
	public ResponseEntity<List<ProductCollection>> findAll() {		
		Enterprise enterprise = authorizationService.getEnterprise();
		List<ProductCollection> list = collectionRepository.findByEnterprise(enterprise);
		return ResponseEntity.ok(list);
	}

	@Transactional
	@PostMapping
	public ResponseEntity<String> save(@RequestBody ProductCollection entity) {
		
		Enterprise enterprise = authorizationService.getEnterprise();
		if(enterprise != null ){
		
			System.out.println(entity);
			entity.setEnterprise(enterprise);
			System.out.println(entity);
			System.out.println(enterprise);
			
			
			ProductCollection saved = collectionRepository.save(entity);
			return ResponseEntity.created(URI.create("/collection/"+saved.getId())).body("Coleção criada com sucesso");
		}else{
			return ResponseEntity.status(500).body("Usuário não está associado a uma empresa!"); 
		}

	}
	
	
}
