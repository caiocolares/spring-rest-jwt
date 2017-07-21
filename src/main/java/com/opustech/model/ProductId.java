package com.opustech.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name="product_id")	
	private String productId;
	
	@Column(name="enterprise_id")
	private Integer enterpriseId;
	
	public ProductId(){
		
	}
	
	public ProductId(String productId, Integer enterpriseId){
		this.productId = productId;
		this.enterpriseId = enterpriseId;
		
	}
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	
}
