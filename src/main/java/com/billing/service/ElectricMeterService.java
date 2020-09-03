package com.billing.service;

import java.util.Map;

import com.billing.entity.ElectricMeter;

public interface ElectricMeterService {

	public void saveElectricMeter(ElectricMeter electricMeter);
	public Map<Long, String> getMeterNumberWithUserId();
	public ElectricMeter findByMeterNumber(String meterNumber);
	public ElectricMeter findById(Long id);
	
}
