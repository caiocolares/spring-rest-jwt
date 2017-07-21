package com.opustech.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
@Entity
@Table(name="users")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	private String username;
	
	private String password;
	
	private Boolean administrator;
	
	@Transient	
	private Date lastPasswordResetDate;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="enterprise_id",referencedColumnName="id")
	private Enterprise enterprise;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		GrantedAuthority authority = new GrantedAuthority() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public String getAuthority() {
				return "DEFAULT";
			}
		};
		return Arrays.asList(authority);
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


}
