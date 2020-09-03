package com.billing.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billing.constant.ElectricityConstant;
import com.billing.entity.ElectricMeter;
import com.billing.repository.ElectricityMeterRepository;
import com.billing.service.ElectricMeterService;

@Service
public class ElectricMeterServiceImpl implements ElectricMeterService{

	@Autowired
	ElectricityMeterRepository electricityMeterRepository;
	
	@Override
	public void saveElectricMeter(ElectricMeter electricMeter) {
		ElectricityConstant.setBaseClassDetails(electricMeter);
		electricMeter.setMeterNumber(String.valueOf(new Random().nextLong()));
		electricityMeterRepository.save(electricMeter);
	}

	@Override
	public Map<Long, String> getMeterNumberWithUserId() {
		
		List<ElectricMeter> electricMeters = electricityMeterRepository.findAll();
		Map<Long, String> map = new HashMap<Long, String>();
		electricMeters.stream().forEach(meter->{
			map.put(meter.getElectricityUser().getId(), meter.getMeterNumber());
		});
		return map;
	}

	@Override
	public ElectricMeter findByMeterNumber(String meterNumber) {
		return electricityMeterRepository.findByMeterNumber(meterNumber);
	}

	@Override
	public ElectricMeter findById(Long id) {
		return electricityMeterRepository.findOne(id);
	}

}
