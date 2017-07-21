package com.opustech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.opustech.model.Specification;
import com.opustech.repository.SpecitificationRepository;
import com.opustech.service.AuthorizationService;

@Controller
@CrossOrigin(origins="*")
public class SpecificationController {
	
	@Autowired
	private SpecitificationRepository specitificationRepository;
	
	@Autowired
	private AuthorizationService authorizationService;
	
	@GetMapping(path="/specification")
	public String list(Model model){
		model.addAttribute("enterprises", authorizationService.listEnterprises());
		return "specification";
	}
	
	@GetMapping(path="/specification/{specification}")
	public String edit(@PathVariable("specification")Specification specification, Model model){
		model.addAttribute("enterprises", authorizationService.listEnterprises());
		model.addAttribute("specification", specification);
		return "specification";
	}
	
	@PostMapping(path="/specification")
	public String save(Specification specification, Model model){
		Specification saved = specitificationRepository.save(specification);		
		return "redirect:/specification/"+saved.getId();
	}
	
	@Transactional
	@PostMapping(path="/specification/{specification}/addvalue")
	public String addValue(@PathVariable("specification") Specification specification, String value, Model model){
		specification.getValues().add(value);
		return "redirect:/specification/"+specification.getId();
	}
	

}
