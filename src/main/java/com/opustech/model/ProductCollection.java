package com.opustech.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Entity
@Data
@Table(name="collection")
public class ProductCollection {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	private String code;
	
	@NotBlank
	private String description;
	
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@Temporal(TemporalType.DATE)
	private Date limitDemand;
	
	@ManyToOne
	@JoinColumn(name="enterprise_id",referencedColumnName="id")
	private Enterprise enterprise;
}
