package com.billing.entity;

import java.util.Date;

import lombok.Data;

@Data
public class BaseClass {

	private Date createdDate;
	private Date modifiedDate;
	private String status;
	
}
