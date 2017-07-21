package com.opustech.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.opustech.model.Enterprise;
import com.opustech.model.Flag;
import com.opustech.repository.FlagRepository;
import com.opustech.service.AuthorizationService;

@CrossOrigin(origins="*")
@Controller
@RequestMapping("/flag")
public class FlagController {

	@Autowired
	private FlagRepository flagRepository;
	
	@Autowired
	private AuthorizationService authorizarionService;
		
	@GetMapping
	public ResponseEntity<List<Flag>> findAll(){
		Enterprise enterprise = authorizarionService.getEnterprise();		
		List<Flag> flags = flagRepository.findByEnterprise(enterprise);		
		return ResponseEntity.ok(flags);			
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Flag> findById(@PathVariable("id") Integer id){				
		Flag flag = flagRepository.findOne(id);		
		return ResponseEntity.ok(flag);			
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody Flag flag){			
		Enterprise enterprise = authorizarionService.getEnterprise();
		flag.setEnterprise(enterprise);
		Flag saved = flagRepository.save(flag);		
		return ResponseEntity.created(URI.create("/flag/"+saved.getId())).build();			
	}
	
	
	
	
	
}
