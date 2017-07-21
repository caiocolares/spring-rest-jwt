package com.opustech.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.opustech.model.User;
import com.opustech.repository.UserRepository;


@Component("userDetailsService")
@Order(Ordered.HIGHEST_PRECEDENCE+10)
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
    UserRepository repository;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = repository.findByUsername(username);
		try{
			user.getAuthorities();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}		
		return user;
	}
}
