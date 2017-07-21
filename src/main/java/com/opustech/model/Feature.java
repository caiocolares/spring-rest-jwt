package com.opustech.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(of={"product","name"})
public class Feature {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String name;	

	@ElementCollection
	@OrderColumn(name="position")
	private Set<String> values = new HashSet<>();

	@JsonIgnore
	@ManyToOne
	@JoinColumns(value={@JoinColumn(name="product_id", referencedColumnName="product_id"),
						@JoinColumn(name="enterprise_id",referencedColumnName="enterprise_id")})
	private Product product;

}
