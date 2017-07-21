package com.opustech.controller;

import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.opustech.model.Demand;
import com.opustech.model.Enterprise;
import com.opustech.model.ProductCollection;
import com.opustech.repository.DemandRepository;
import com.opustech.service.AuthorizationService;

@RequestMapping("/demand")
@RestController
@CrossOrigin("*")
public class DemandController {

	@Autowired
	private DemandRepository demandRepository;
	
	@Autowired
	private AuthorizationService authorizationService;
	
	@GetMapping
	public @ResponseBody ResponseEntity<List<Demand>> findAll(){		
		Enterprise enterprise = authorizationService.getEnterprise();		
		List<Demand> list = demandRepository.findByEnterprise(enterprise);
		return ResponseEntity.ok(list);
	}

	@GetMapping("/collection/{id}")
	public @ResponseBody ResponseEntity<List<Demand>> findByCollection(@PathVariable("id") ProductCollection collection){		
		Enterprise enterprise = authorizationService.getEnterprise();		
		List<Demand> list = demandRepository.findByEnterpriseAndCollection(enterprise, collection);
		return ResponseEntity.ok(list);
	}

	@GetMapping("/{id}")
	public @ResponseBody ResponseEntity<Demand> findById(@PathVariable("id") Integer id){		
		Demand demand = demandRepository.findOne(id);
		return ResponseEntity.ok(demand);
	}
	
	@PostMapping
	public @ResponseBody ResponseEntity<?> save(@Valid @RequestBody Demand demand){
		Enterprise enterprise = authorizationService.getEnterprise();
		if(enterprise != null){
			if(demand.getId() == null || demand.getId() == 0){
				demand.setInsertedDate(new Date());
			}
			demand.setEnterprise(enterprise);
			demand.setUpdatedDate(new Date());
			Demand saved = demandRepository.save(demand);
			return ResponseEntity.created(URI.create("/demand/"+saved.getId())).build();
		}else{
			return ResponseEntity.status(500).body("");
		}
	}
}
