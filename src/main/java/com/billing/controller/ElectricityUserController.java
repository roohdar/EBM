package com.billing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.billing.dto.ElectricityUserDTO;
import com.billing.entity.ElectricMeter;
import com.billing.entity.ElectricityProvider;
import com.billing.entity.ElectricityUser;
import com.billing.enums.PageView;
import com.billing.service.ElectricMeterService;
import com.billing.service.ElectricityProviderService;
import com.billing.service.ElectricityUserService;
import com.billing.service.EncryptDecryptUtils;

@Controller
@RequestMapping(value = "/user")
public class ElectricityUserController {

	@Autowired
	private ElectricityUserService electricityUserService;

	@Autowired
	private ElectricityProviderService electricityProviderService;
	
	@Autowired
	private ElectricMeterService electricMeterService;

	@RequestMapping(value = "/registration",method = RequestMethod.GET)
	private String getElectricityProvider(ModelMap modelMap) {
		modelMap.put("electricityUserDTO", electricityUserService.getElectricityUser());
		modelMap.put("electricityProviderDTO", electricityProviderService.getAllElectricityProviders());

		return PageView.ELECTRICITY_USER_REGISRATION_PAGE.getView();
	}


	@RequestMapping(value = "/registerUser",method = RequestMethod.POST)
	private String registerElectricityProvider(@ModelAttribute("electricityUserDTO") ElectricityUserDTO electricityUserDTO, ModelMap modelMap) {

		ElectricityUser byEmail = electricityUserService.getByEmail(electricityUserDTO.getEmail());
		if(byEmail==null) {
			ElectricityProvider electricityProvider = electricityProviderService.getById(Long.parseLong(EncryptDecryptUtils.decrypt(electricityUserDTO.getElectricityProvider())));
			ElectricityUser saveElectricityUser = electricityUserService.saveElectricityUser(electricityUserDTO,electricityProvider);
			
			ElectricMeter electricMeter = new ElectricMeter();
			electricMeter.setElectricityUser(saveElectricityUser);
			electricMeter.setProvider(electricityProvider);
			electricMeterService.saveElectricMeter(electricMeter);
			
			
			return "redirect:/user/getAllUsers";
		}else {
			modelMap.put("electricityUserDTO", electricityUserService.getElectricityUser());
			modelMap.put("electricityProviderDTO", electricityProviderService.getAllElectricityProviders());
			modelMap.put("msg","This User is already available");
			return PageView.ELECTRICITY_USER_REGISRATION_PAGE.getView();
		}
	}

	@RequestMapping(value = "/getAllUsers",method = RequestMethod.GET)
	private String getAllElectricityUsers(ModelMap modelMap) {
		modelMap.put("electricityUserDTOs", electricityUserService.getAllElectricityUser());
		return PageView.ELECTRICITY_USER_LIST_PAGE.getView();
	}

}
