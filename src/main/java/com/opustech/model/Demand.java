package com.opustech.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Demand implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="enterprise_id", referencedColumnName="id")
	private Enterprise enterprise;
	
	@ManyToOne
	@JoinColumn(name="colleciton_id", referencedColumnName="id")
	private ProductCollection collection;
	
	@ManyToOne
	@JoinColumn(name="category_id", referencedColumnName="id")
	private Category category;
	
	private Integer quantity;
	
	@JsonIgnore
	@OneToMany
	@JoinColumns({@JoinColumn(name="product_id",referencedColumnName="id",updatable=false, insertable=false),
		@JoinColumn(name="enterprise_id",referencedColumnName="id",updatable=false, insertable=false)})
	private List<Product> products;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date insertedDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	
}
