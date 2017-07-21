package com.opustech.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of="id")
@Entity
public class Enterprise {
	
	@Id	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@NotBlank
	@Column(name="name")
	private String socialName;
	@NotBlank
	private String cnpj;
	
	private String fantasy;
	
	private String url;
	
	private String email;

    private String phone;
    
    private Boolean active;

}
