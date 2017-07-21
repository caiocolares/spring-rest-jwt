package com.opustech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping(path={"/",""})
	public String index(Model model){
		return "redirect:/catalog";
	}	
	
	@RequestMapping(path="/login")
	public String login(Model model){
		model.addAttribute("msg", "Seja bem vindo");
		return "login";
	}
	
}
