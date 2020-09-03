package com.billing.service;

import java.util.List;

import com.billing.dto.ElectricityMeterReadingDTO;
import com.billing.entity.ElectricityMeterReading;

public interface ElectricityMeterReadingService {

	public ElectricityMeterReading saveMeterReading(ElectricityMeterReadingDTO electricityMeterReadingDTO);
	public List<ElectricityMeterReadingDTO> getAllReadingsByMeterNumber(String meterNumber);
	public List<ElectricityMeterReadingDTO> getAllReadingsByMeterNumberAndYear(String meterNumber,String year);
	
}
