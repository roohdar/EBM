package com.billing.service;

import java.util.List;

import com.billing.dto.ElectricityUserDTO;
import com.billing.entity.ElectricityProvider;
import com.billing.entity.ElectricityUser;

public interface ElectricityUserService {

	public ElectricityUserDTO getElectricityUser();
	public ElectricityUser saveElectricityUser(ElectricityUserDTO electricityUserDTO, ElectricityProvider electricityProvider);
	public List<ElectricityUserDTO> getAllElectricityUser();
	public List<ElectricityUserDTO> getAllElectricityUserByProviders(Long providorId);
	public ElectricityUser getByEmail(String email);

}
