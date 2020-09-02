package com.billing.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billing.constant.ElectricityConstant;
import com.billing.entity.ElectricMeter;
import com.billing.entity.ElectricityUser;
import com.billing.repository.ElectricityMeterRepository;

@Service
public class ElectricMeterServiceImpl implements ElectricMeterService{

	@Autowired
	ElectricityMeterRepository electricityMeterRepository;
	
	@Override
	public void saveElectricMeter(ElectricMeter electricMeter) {
		ElectricityConstant.setBaseClassDetails(electricMeter);
		electricMeter.setMeterNumber(String.valueOf(new Random().nextInt()));
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

}
