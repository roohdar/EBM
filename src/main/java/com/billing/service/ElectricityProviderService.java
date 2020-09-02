package com.billing.service;

import java.util.List;

import com.billing.dto.ElectricityProviderDTO;
import com.billing.entity.ElectricityProvider;

public interface ElectricityProviderService {
	
	public ElectricityProviderDTO getElectricityProvider();
	public void saveElectricityProvider(ElectricityProviderDTO electricityProviderDTO);
	public List<ElectricityProviderDTO> getAllElectricityProviders();
	public ElectricityProvider getById(Long id);

}
