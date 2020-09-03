package com.billing.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ElectricityMeterReadingDTO{

	private String encryptedId;
	private Date startDate;
	private Date endDate;
	private String unit;
	private String meterencryptedId;
	
}
