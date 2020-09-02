package com.billing.dto;

import lombok.Data;

@Data
public class ElectricityProviderDTO {

	private String encryptedId;
	private String companyName;
	private String city;
	private Long zipCode;
	private String state;
	private String email;
	private String password;
	
}
