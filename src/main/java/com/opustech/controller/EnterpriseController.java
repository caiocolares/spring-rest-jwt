package com.opustech.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.opustech.model.Enterprise;
import com.opustech.repository.EnterpriseRepository;
import com.opustech.service.AuthorizationService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {
	
	@Autowired
	private EnterpriseRepository enterpriseRepository;
		
	@SuppressWarnings("unused")
	@Autowired
	private AuthorizationService authorizationService;
	
	private static final Logger logger = LoggerFactory.getLogger(EnterpriseController.class);

	
	
	@RequestMapping(path="/{id}")
	@PreAuthorize("@authorizationService.canAccessEnterprise(#id)")
	public ResponseEntity<Enterprise> findEnterpriseById(@PathVariable("id") Integer id){
		
		logger.debug("Enterprise","Id :"+id);
		
		 Enterprise enterprise = enterpriseRepository.findOne(id);
		 
		 if(enterprise == null){
			 enterprise = new Enterprise();
			 enterprise.setId(id);
			 enterprise.setSocialName("Nenhuma empresa encontrada com id "+id);
			 return ResponseEntity.status(404).body(enterprise);
		 }
		 
		 return ResponseEntity.ok(enterprise);
		 
		 
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@PreAuthorize("@authorizationService.isAdministrator()")
	public ResponseEntity<String> save(@RequestBody Enterprise enterprise){
		Enterprise saved = enterpriseRepository.save(enterprise);
		
		return ResponseEntity.created(URI.create("/enterprise/"+saved.getId())).body("Empresa salva com sucesso!");
	}
			
}
