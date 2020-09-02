package com.billing.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ElectricityProvider extends BaseClass{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String companyName;
	private String city;
	private Long zipCode;
	private String state;
	private String email;
	private String password;
}
