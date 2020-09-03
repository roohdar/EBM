package com.billing.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.OptionalDouble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billing.constant.ElectricityConstant;
import com.billing.dto.ElectricityUserDTO;
import com.billing.entity.ElectricityMeterReading;
import com.billing.entity.ElectricityProvider;
import com.billing.entity.ElectricityUser;
import com.billing.reflection.JavaReflection;
import com.billing.repository.ElectricityMeterReadingRepository;
import com.billing.repository.ElectricityUserRepository;
import com.billing.service.ElectricMeterService;
import com.billing.service.ElectricityUserService;
import com.billing.service.EncryptDecryptUtils;

@Service
public class ElectricityUserServiceImpl implements ElectricityUserService{

	@Autowired
	private ElectricityUserRepository electricityUserRepository;

	@Autowired
	private ElectricMeterService electricMeterService;
	
	@Autowired
	ElectricityMeterReadingRepository electricityMeterReadingRepository;

	@Override
	public ElectricityUserDTO getElectricityUser() {
		return new ElectricityUserDTO();
	}

	@Override
	public ElectricityUser saveElectricityUser(ElectricityUserDTO electricityUserDTO, ElectricityProvider electricityProvider) {
		
		ElectricityUser electricityUser;
		
		if(electricityUserDTO.getEncryptedId()==null) {
			electricityUser = new ElectricityUser();
			JavaReflection.copyMyObject(electricityUserDTO, electricityUser);
			ElectricityConstant.setBaseClassDetails(electricityUser);
			electricityUser.setPassword(EncryptDecryptUtils.encrypt(electricityUserDTO.getPassword()));
		}else 
			electricityUser = electricityUserRepository.findOne(Long.parseLong(EncryptDecryptUtils.decrypt(electricityUserDTO.getEncryptedId())));

		electricityUser.setElectricityProvider(electricityProvider);
		return electricityUserRepository.save(electricityUser);

	}

	@Override
	public List<ElectricityUserDTO> getAllElectricityUser() {
		List<ElectricityUser> electricityUsers = electricityUserRepository.findAll();
		List<ElectricityUserDTO> electricityUserDTOs = new ArrayList<ElectricityUserDTO>();

		Map<Long, String> meterNumberWithUserId = electricMeterService.getMeterNumberWithUserId();

		electricityUsers.stream().forEach(user->{
			ElectricityUserDTO dto = setToDTO(user);
			dto.setMeterNumber(meterNumberWithUserId.get(user.getId()));
			
			List<ElectricityMeterReading> findByMeterNumber = electricityMeterReadingRepository.findByMeterNumber(dto.getMeterNumber());
			try {
				Double average = findByMeterNumber.stream() .mapToDouble(mr -> mr.getUnit()).average().getAsDouble();
				dto.setAverageelectricityconsumption(String.valueOf(average));
			} catch (NoSuchElementException e) {
			}
			electricityUserDTOs.add(dto);
		});

		return electricityUserDTOs;
	}

	@Override
	public ElectricityUser getByEmail(String email) {
		// TODO Auto-generated method stub
		return electricityUserRepository.findByEmail(email);
	}

	@Override
	public List<ElectricityUserDTO> getAllElectricityUserByProviders(Long providorId) {
		
		List<ElectricityUser> electricityUsers = electricityUserRepository.getAllElectricityUserByProviders(providorId);
		
		List<ElectricityUserDTO> electricityUserDTOs = new ArrayList<ElectricityUserDTO>();

		Map<Long, String> meterNumberWithUserId = electricMeterService.getMeterNumberWithUserId();

		electricityUsers.stream().forEach(user->{
			ElectricityUserDTO dto = setToDTO(user);
			dto.setMeterNumber(meterNumberWithUserId.get(user.getId()));
			
			List<ElectricityMeterReading> findByMeterNumber = electricityMeterReadingRepository.findByMeterNumber(dto.getMeterNumber());
			try {
				Double average = findByMeterNumber.stream() .mapToDouble(mr -> mr.getUnit()).average().getAsDouble();
				dto.setAverageelectricityconsumption(String.valueOf(average));
			} catch (NoSuchElementException e) {
			}
			
			electricityUserDTOs.add(dto);
		});
		return electricityUserDTOs;
	}

	@Override
	public ElectricityUserDTO setToDTO(ElectricityUser electricityUser) {
		ElectricityUserDTO dto = new ElectricityUserDTO();
		JavaReflection.copyMyObject(electricityUser, dto);
		dto.setEncryptedId(EncryptDecryptUtils.encrypt(String.valueOf(electricityUser.getId())));
		dto.setElectricityProvider(electricityUser.getElectricityProvider().getCompanyName());
		return dto;
	}

	@Override
	public ElectricityUserDTO getElectricityUserById(Long id) {
		ElectricityUser electricityUser = electricityUserRepository.findOne(id);
		ElectricityUserDTO dto = setToDTO(electricityUser);
		return dto;
	}
}
