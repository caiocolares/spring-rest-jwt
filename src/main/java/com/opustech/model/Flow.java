package com.opustech.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Flow {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
		
	@JsonIgnore
	@ManyToOne
	@JoinColumns(value={@JoinColumn(name="product_id", referencedColumnName="product_id"),
						@JoinColumn(name="enterprise_id",referencedColumnName="enterprise_id")})
	private Product product;	
	
	@ManyToOne
	@JoinColumn(referencedColumnName="id", name="sector_id")
	private Sector sector;
	
	private Integer expectedTime;
	
	@Temporal(TemporalType.DATE)
	private Date expectedStart;
	
	@Temporal(TemporalType.DATE)
	private Date expectedEnd;

	@Temporal(TemporalType.DATE)
	private Date realizedStart;
	
	@Temporal(TemporalType.DATE)
	private Date realizedEnd;
	
	private Integer position;
	
	
	public void setSector(Sector sector, Date expectedStart){
		this.sector = sector;
		this.expectedStart = expectedStart;
		this.expectedTime = sector.getProcessTime();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(expectedStart);
		calendar.add(Calendar.DATE, sector.getProcessTime());
		this.expectedEnd = calendar.getTime();
				
	}
	
}
