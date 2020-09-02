package com.billing.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class ElectricityUser extends BaseClass{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private Long zipCode;
	private String state;
	@Column(unique = true)
	private String email;
	private String password;
	private String mobile;
	
	@OneToOne
	private ElectricityProvider electricityProvider;
	
}
