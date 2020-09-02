package com.billing.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billing.constant.ElectricityConstant;
import com.billing.dto.ElectricityUserDTO;
import com.billing.entity.ElectricityProvider;
import com.billing.entity.ElectricityUser;
import com.billing.reflection.JavaReflection;
import com.billing.repository.ElectricityUserRepository;

@Service
public class ElectricityUserServiceImpl implements ElectricityUserService{

	@Autowired
	private ElectricityUserRepository electricityUserRepository;

	@Autowired
	private ElectricMeterService electricMeterService;

	@Override
	public ElectricityUserDTO getElectricityUser() {
		return new ElectricityUserDTO();
	}

	@Override
	public ElectricityUser saveElectricityUser(ElectricityUserDTO electricityUserDTO, ElectricityProvider electricityProvider) {

		ElectricityUser electricityUser = new ElectricityUser();

		JavaReflection.copyMyObject(electricityUserDTO, electricityUser);

		ElectricityConstant.setBaseClassDetails(electricityUser);
		electricityUser.setPassword(EncryptDecryptUtils.encrypt(electricityUserDTO.getPassword()));
		electricityUser.setElectricityProvider(electricityProvider);
		return electricityUserRepository.save(electricityUser);

	}

	@Override
	public List<ElectricityUserDTO> getAllElectricityUser() {
		List<ElectricityUser> electricityUsers = electricityUserRepository.findAll();
		List<ElectricityUserDTO> electricityUserDTOs = new ArrayList<ElectricityUserDTO>();

		Map<Long, String> meterNumberWithUserId = electricMeterService.getMeterNumberWithUserId();

		electricityUsers.stream().forEach(user->{
			ElectricityUserDTO dto = new ElectricityUserDTO();
			JavaReflection.copyMyObject(user, dto);
			dto.setEncryptedId(EncryptDecryptUtils.encrypt(String.valueOf(user.getId())));
			dto.setElectricityProvider(user.getElectricityProvider().getCompanyName());
			dto.setMeterNumber(meterNumberWithUserId.get(user.getId()));
			electricityUserDTOs.add(dto);
		});

		return electricityUserDTOs;
	}

	@Override
	public ElectricityUser getByEmail(String email) {
		// TODO Auto-generated method stub
		return electricityUserRepository.findByEmail(email);
	}
}
