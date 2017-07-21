package com.opustech.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of="id")
@Data
@Entity
public class Product {
	
	@EmbeddedId
	private ProductId id;
	private String name;
	private String description;
	private String image;
	
	private String detail;
	
	@Column(name="enterprise_id", updatable=false, insertable=false)
	private Integer enteprise;
	
	
	
	@OneToMany(mappedBy="product")
	@OrderColumn(name="position",insertable=false,updatable=false)
	private List<Flow> flow;
	
	@ElementCollection
	@CollectionTable(name="product_images",
					joinColumns={@JoinColumn(name="product_id",referencedColumnName="product_id"),
								 @JoinColumn(name="enterprise_id",referencedColumnName="enterprise_id")})
	private Set<String> images;
	
	@OneToMany(mappedBy="product")
	private Set<Feature> features;
	
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="category_id",referencedColumnName="id")
	private Category category;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="collection_id",referencedColumnName="id")
	private ProductCollection collection;
	
	public Set<Feature> getFeatures(){
		if(this.features == null){
			this.features = new HashSet<>();
		}
		return this.features;
	}

}
