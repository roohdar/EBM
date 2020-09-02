package com.billing.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class ElectricMeter extends BaseClass{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String meterNumber;
	
	@ManyToOne
	private ElectricityProvider provider;
	@OneToOne
	private ElectricityUser electricityUser;
	
}
