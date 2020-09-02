package com.billing.constant;

import java.util.Date;

import com.billing.entity.BaseClass;

public class ElectricityConstant {

	public static String STATUS_ACTIVE = "active";
	public static String STATUS_DELETE = "delete";
	
	public static void setBaseClassDetails(BaseClass object){
		object.setCreatedDate(new Date());
		object.setModifiedDate(new Date());
		object.setStatus(ElectricityConstant.STATUS_ACTIVE);

	}
}
