package com.billing.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billing.constant.ElectricityConstant;
import com.billing.dto.ElectricityProviderDTO;
import com.billing.entity.ElectricityProvider;
import com.billing.reflection.JavaReflection;
import com.billing.repository.ElectricityProviderRepository;
import com.billing.service.ElectricityProviderService;
import com.billing.service.EncryptDecryptUtils;

@Service
public class ElectricityProviderServiceImpl implements ElectricityProviderService {

	@Autowired
	private ElectricityProviderRepository electricityProviderRepository;

	@Override
	public ElectricityProviderDTO getElectricityProvider() {
		ElectricityProviderDTO electricityProviderDTO = new ElectricityProviderDTO();
		return electricityProviderDTO;
	}

	@Override
	public void saveElectricityProvider(ElectricityProviderDTO electricityProviderDTO) {
		ElectricityProvider electricityProvider = new ElectricityProvider();
		JavaReflection.copyMyObject(electricityProviderDTO, electricityProvider);

		ElectricityConstant.setBaseClassDetails(electricityProvider);
		electricityProvider.setPassword(EncryptDecryptUtils.encrypt(electricityProviderDTO.getPassword()));

		electricityProviderRepository.save(electricityProvider);
	}

	@Override
	public List<ElectricityProviderDTO> getAllElectricityProviders() {
		// TODO Auto-generated method stub
		List<ElectricityProvider> findAll = electricityProviderRepository.findAll();
		List<ElectricityProviderDTO> electricityProviders = new ArrayList<ElectricityProviderDTO>();
		findAll.stream().forEach(object->{
			ElectricityProviderDTO dto= new ElectricityProviderDTO();
			JavaReflection.copyMyObject(object, dto);
			dto.setEncryptedId(EncryptDecryptUtils.encrypt(String.valueOf(object.getId())));
			electricityProviders.add(dto);
		});
		return electricityProviders;
	}

	@Override
	public ElectricityProvider getById(Long id) {
		return electricityProviderRepository.findOne(id);
	}
}
