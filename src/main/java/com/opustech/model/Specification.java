package com.opustech.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(of="id")
public class Specification {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	@ElementCollection
	@OrderColumn(name="position")
	private List<String> values = new ArrayList<>();	

	@ManyToOne
	@JoinColumn(name="enterprise_id",referencedColumnName="id")
	private Enterprise enterprise;
		
}
