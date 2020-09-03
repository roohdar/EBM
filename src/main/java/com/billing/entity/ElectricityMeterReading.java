package com.billing.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class ElectricityMeterReading extends BaseClass{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Date startDate;
	private Date endDate;
	private Double unit;

	@ManyToOne
	private ElectricMeter electricMeter;
	
}
