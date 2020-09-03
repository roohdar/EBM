package com.billing.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billing.constant.ElectricityConstant;
import com.billing.dto.ElectricityMeterReadingDTO;
import com.billing.entity.ElectricityMeterReading;
import com.billing.reflection.JavaReflection;
import com.billing.repository.ElectricityMeterReadingRepository;
import com.billing.service.ElectricMeterService;
import com.billing.service.ElectricityMeterReadingService;
import com.billing.service.EncryptDecryptUtils;

@Service
public class ElectricityMeterReadingServiceImpl implements ElectricityMeterReadingService{

	@Autowired
	private ElectricityMeterReadingRepository electricityMeterReadingRepository;

	@Autowired
	private ElectricMeterService electricMeterService;

	@Override
	public ElectricityMeterReading saveMeterReading(ElectricityMeterReadingDTO electricityMeterReadingDTO) {

		ElectricityMeterReading electricityMeterReading = new ElectricityMeterReading();
		ElectricityConstant.setBaseClassDetails(electricityMeterReading);

		JavaReflection.copyMyObject(electricityMeterReadingDTO, electricityMeterReading);
		electricityMeterReading.setUnit(Double.parseDouble(electricityMeterReadingDTO.getUnit()));
		electricityMeterReading.setElectricMeter(electricMeterService.findById(Long.parseLong(EncryptDecryptUtils.decrypt(electricityMeterReadingDTO.getMeterencryptedId()))));
		return electricityMeterReadingRepository.save(electricityMeterReading);
	}

	@Override
	public List<ElectricityMeterReadingDTO> getAllReadingsByMeterNumber(String meterNumber) {
		List<ElectricityMeterReading> electricityMeterReadings = electricityMeterReadingRepository.findByMeterNumber(meterNumber);

		List<ElectricityMeterReadingDTO> dtos = new ArrayList<ElectricityMeterReadingDTO>();

		electricityMeterReadings.forEach(mr->{
			ElectricityMeterReadingDTO dto = new ElectricityMeterReadingDTO();
			dto.setEncryptedId(EncryptDecryptUtils.encrypt(String.valueOf(mr.getId())));
			dto.setStartDate(mr.getStartDate());
			dto.setEndDate(mr.getEndDate());
			dto.setUnit(String.valueOf(mr.getUnit()));
			dtos.add(dto);
		});

		return dtos;
	}

	@Override
	public List<ElectricityMeterReadingDTO> getAllReadingsByMeterNumberAndYear(String meterNumber, String year) {

		List<ElectricityMeterReading> electricityMeterReadings = electricityMeterReadingRepository.findByMeterNumberAndYear(meterNumber, Double.parseDouble(year));

		List<ElectricityMeterReadingDTO> dtos = new ArrayList<ElectricityMeterReadingDTO>();

		electricityMeterReadings.forEach(mr->{
			ElectricityMeterReadingDTO dto = new ElectricityMeterReadingDTO();
			dto.setEncryptedId(EncryptDecryptUtils.encrypt(String.valueOf(mr.getId())));
			dto.setStartDate(mr.getStartDate());
			dto.setEndDate(mr.getEndDate());
			dto.setUnit(String.valueOf(mr.getUnit()));
			dtos.add(dto);
		});

		return dtos;
	}

}
