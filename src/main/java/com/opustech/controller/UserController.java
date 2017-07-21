package com.opustech.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.opustech.model.Enterprise;
import com.opustech.model.User;
import com.opustech.repository.UserRepository;
import com.opustech.service.AuthorizationService;

@RequestMapping(path="/user")
@Controller
@CrossOrigin(origins="*")
public class UserController {
	
	private Md5PasswordEncoder encodeMD5 = new Md5PasswordEncoder();
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorizationService authorizationService;
	
	@GetMapping
	public ResponseEntity<List<User>> list(){
		Enterprise enterprise = authorizationService.getEnterprise();
		return ResponseEntity.ok(userRepository.findByEnterprise(enterprise));
	}
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody User user){		
		
		Enterprise enterprise = authorizationService.getEnterprise();
		
		String password = user.getPassword();
		password = encodeMD5.encodePassword(password, null);
		
		user.setPassword(password);
		user.setEnterprise(enterprise);
		
		User saved = userRepository.save(user);					
		return ResponseEntity.created(URI.create("/user/"+saved.getId())).build();
	}

}
