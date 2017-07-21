package com.opustech.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Variation {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name="enterprise_id",referencedColumnName="id")
	private Enterprise enterprise;
	
	@OneToMany(mappedBy="variation",cascade=CascadeType.ALL)
	private Set<VariationValue> values;
	
}
