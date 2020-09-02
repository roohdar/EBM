package com.billing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.billing.dto.ElectricityProviderDTO;
import com.billing.dto.ElectricityUserDTO;
import com.billing.enums.PageView;
import com.billing.service.ElectricityProviderService;
import com.billing.service.ElectricityUserService;
import com.billing.service.EncryptDecryptUtils;

@Controller
@RequestMapping(value = "/provider")
public class ElectricityProviderController {

	@Autowired
	private ElectricityProviderService electricityProviderService;
	
	@Autowired
	private ElectricityUserService electricityUserService;

	@RequestMapping(value = "/registration",method = RequestMethod.GET)
	private String getElectricityProvider(ModelMap modelMap) {
		modelMap.put("electricityProviderDTO", electricityProviderService.getElectricityProvider());
		return PageView.ELECTRICITY_PROVIDER_REGISRATION_PAGE.getView();
	}


	@RequestMapping(value = "/registerProvider",method = RequestMethod.POST)
	private String registerElectricityProvider(@ModelAttribute("electricityProviderDTO") ElectricityProviderDTO electricityProviderDTO) {

		electricityProviderService.saveElectricityProvider(electricityProviderDTO);

		return "redirect:/provider/getAllProviders";
	}
	
	
	@RequestMapping(value = "/getAllProviders",method = RequestMethod.GET)
	private String getAllElectricityUsers(ModelMap modelMap) {
		modelMap.put("electricityProviderDTOs", electricityProviderService.getAllElectricityProviders());
		return PageView.ELECTRICITY_PROVIDER_LIST_PAGE.getView();
	}
	
	@RequestMapping(value = "/getCustomers/{encryptedId}",method = RequestMethod.GET)
	private String getAllCustomers(ModelMap modelMap, @PathVariable("encryptedId") String encryptedId) {
		
		List<ElectricityUserDTO> allElectricityUserByProviders = electricityUserService.getAllElectricityUserByProviders(Long.parseLong(EncryptDecryptUtils.decrypt(encryptedId)));
		
		modelMap.put("electricityUserDTOs", allElectricityUserByProviders);
		
		return PageView.ELECTRICITY_PROVIDER_USER_LIST_PAGE.getView();
	}

}
