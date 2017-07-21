package com.opustech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.opustech.model.Variation;
import com.opustech.model.VariationValue;
import com.opustech.repository.VariationRepository;
import com.opustech.service.AuthorizationService;
import com.opustech.storage.StorageService;

@CrossOrigin(origins="*")
@Controller
public class VariationController {

	@Autowired
	private VariationRepository variationRepository;
	
	@Autowired
	private AuthorizationService authorizationService;
	
	@Autowired
	private StorageService storageService;
	
	@GetMapping(path="/variation")
	public String home(Model model){
		model.addAttribute("enterprise", authorizationService.listEnterprises());
		return "variation";
	}
	
	@PostMapping(path="/variation")
	public String save(Variation variation, Model model){
		Variation saved = variationRepository.save(variation);
		
		return "redirect:/variation/"+saved.getId();
	}
	
	@GetMapping(path="/variation/{variation}")
	public String edit(@PathVariable("variation") Variation variation, Model model){
		model.addAttribute("enterprise", authorizationService.listEnterprises());
		model.addAttribute("variation", variation);
		return "variation";
	}
	
	@Transactional
	@PostMapping(path="/variation/{variation}/addvalue")
	public String addValue(@PathVariable("variation") Variation variation, 
						   MultipartFile file, String name, String color, Model model){
		
		VariationValue value = new VariationValue();
		variation.setName(name);
		
		value.setVariation(variation);
		if(file != null && !file.isEmpty()){
			value.setImage(file.getOriginalFilename());
			storageService.store(file, variation.getEnterprise().getCnpj()+"/variations");
		}else{
			value.setImage(color);
		}
		
		variation.getValues().add(value);
		
		return "redirect:/variation/"+variation.getId();
	}
	
}
