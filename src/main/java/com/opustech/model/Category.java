package com.opustech.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
public class Category {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message="Codigo da categoria abrigatória")
	@Length(min=1,max=10,message="Tamanho do código da categoria deve variar entre 1 e 10")
	private String code;
	
	@NotBlank(message="Nome da categoria obrigatória")
	@Length(min=4,max=25,message="Tamanho do código da categoria deve variar entre 5 e 25")
	private String name;

	@ManyToOne
	@JoinColumn(name="enterprise_id",referencedColumnName="id")
	private Enterprise enterprise;
	
}
