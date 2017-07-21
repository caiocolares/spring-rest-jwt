package com.opustech.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opustech.model.Enterprise;
import com.opustech.model.User;
import com.opustech.repository.EnterpriseRepository;
import com.opustech.repository.UserRepository;

@Service
public class AuthorizationService {

	//insert into users(administrator,name,username,password) values(true,'Administrador do Sistema','admin','202cb962ac59075b964b07152d234b70')
	public Boolean canAccessEnterprise(Integer enterpriseId){
		User user = getCurrentUser();	
		if(user.getAdministrator()){
			return true;
		}		
		return user.getEnterprise().getId().equals(enterpriseId);
	}
	
	public Boolean isAdministrator(){
		User user = getCurrentUser();		
		return user.getAdministrator();
	}
	
	private User getCurrentUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getPrincipal().equals("anonymousUser")){
			return null;
		}
		return  (User) authentication.getPrincipal();
	}
	
	private Md5PasswordEncoder encodeMD5 = new Md5PasswordEncoder();

	@Autowired
    UserRepository repository;
	
	@Autowired
	EnterpriseRepository enterpriseRepository;

	@Transactional(readOnly=true)
	public User findUserByLogin(String username, String password, List<GrantedAuthority> grantedAuths) throws BadCredentialsException {
		if (username.isEmpty()) {
			throw new BadCredentialsException("Login não preenchido!");
		}
		if (password.isEmpty()) {
			throw new BadCredentialsException("Senha obrigatória!");
		}
		
		User user = repository.findByUsername(username);
		if (user != null) {
			if (user.getPassword().equals(encodeMD5.encodePassword(password, null))) {
				grantedAuths.addAll(user.getAuthorities());
	            grantedAuths.add(new SimpleGrantedAuthority("ROLE_DEFAULT"));
				return user;
			} else {
				throw new BadCredentialsException("Senha incorreta");
			}
		}
		throw new BadCredentialsException("Usuário nao encontrado");
	}
	
	@Transactional(readOnly=true)
	public List<Enterprise> listEnterprises(){
		User user = getCurrentUser();
		
		if(user == null){
			return new ArrayList<>();
		}
		
		if(user.getAdministrator()){
			return enterpriseRepository.findAll();					
		}
		
		User user2 = repository.findOne(user.getId());
		
		return Arrays.asList(user2.getEnterprise());
	}

	@Transactional(readOnly=true)
	public Enterprise getEnterprise(){
		return getCurrentUser().getEnterprise();		
	}
	
	
	
	
}
