package com.opustech.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opustech.model.Category;
import com.opustech.model.Enterprise;
import com.opustech.repository.CategoryRepository;
import com.opustech.service.AuthorizationService;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins="*")
public class CategoryController {
	
	@Autowired
	private AuthorizationService authorizationService;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping
	public ResponseEntity<List<Category>> list(){
		//List<Category> categories = categoryRepository.findByEnterpriseIdIn(authorizationService.listEnterprises().stream().map(e->e.getId()).collect(Collectors.toList()));
		List<Category> categories = categoryRepository.findByEnterpriseId(authorizationService.getEnterprise().getId());
		return ResponseEntity.ok(categories);
	}
	
	@GetMapping(path="/{id}")
	public ResponseEntity<Category> findById(@PathVariable("id") Category category){		
		return ResponseEntity.ok(category);						
	}

	@PostMapping
	public ResponseEntity<String> save(@Valid @RequestBody Category category){
		
			Enterprise enterprise = authorizationService.getEnterprise();
			if(enterprise != null ){
			
				System.out.println(category);
				category.setEnterprise(enterprise);
				Category saved = categoryRepository.save(category);
				return ResponseEntity.created(URI.create("/category/"+saved.getId())).body("Categoria criada com sucesso");
			}else{
				return ResponseEntity.status(500).body("Usuário não está associado a uma empresa!"); 
			}
			
	}

}
