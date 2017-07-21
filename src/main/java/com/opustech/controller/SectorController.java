package com.opustech.controller;

import java.net.URI;
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

import com.opustech.model.Enterprise;
import com.opustech.model.Sector;
import com.opustech.repository.SectorRepository;
import com.opustech.service.AuthorizationService;

@RequestMapping("/sector")
@RestController
@CrossOrigin("*")
public class SectorController {

	@Autowired
	private SectorRepository sectorRepository;
	
	@Autowired
	private AuthorizationService authorizationService;
	
	@GetMapping
	public @ResponseBody ResponseEntity<List<Sector>> findAll(){		
		Enterprise enterprise = authorizationService.getEnterprise();		
		List<Sector> list = sectorRepository.findByEnterprise(enterprise);
		return ResponseEntity.ok(list);
	}
	
	@PostMapping
	public @ResponseBody ResponseEntity<?> save(@Valid @RequestBody Sector sector){
		Enterprise enterprise = authorizationService.getEnterprise();
		if(enterprise != null){
			
			sector.setEnterprise(enterprise);
			Sector saved = sectorRepository.save(sector);
			return ResponseEntity.created(URI.create("/sector/"+saved.getId())).build();
		}else{
			return ResponseEntity.status(500).body("");
		}
	}
	
	@GetMapping("/{id}")
	public @ResponseBody ResponseEntity<Sector> findById(@PathVariable("id") Integer id){		
		Sector sector = sectorRepository.findOne(id);
		return ResponseEntity.ok(sector);
	}
	
}
