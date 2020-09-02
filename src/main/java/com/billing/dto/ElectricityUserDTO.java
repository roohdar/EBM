package com.billing.dto;

import lombok.Data;

@Data
public class ElectricityUserDTO {

	private String encryptedId;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private Long zipCode;
	private String state;
	private String email;
	private String password;
	private String mobile;
	private String electricityProvider;
	private String meterNumber;

}
