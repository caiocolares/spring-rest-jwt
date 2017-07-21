package com.opustech.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
@Entity
public class Sector implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="enterprise_id", referencedColumnName="id")
	private Enterprise enterprise;
	
	@NotBlank(message="Codigo do setor abrigatória")
	@Length(min=1,max=10,message="Tamanho do código do setor deve variar entre 1 e 10")
	private String code;
	
	@NotBlank(message="Descrição do Setor obrigatório")
	@Length(min=5,max=25,message="Tamanho da descrição do setor deve variar entre 5 e 25")
	private String description;
	
	@Range(min=1,max=20,message="Duração do processo deve variar entre 1 e 20 dias")
	private Integer processTime;

}
