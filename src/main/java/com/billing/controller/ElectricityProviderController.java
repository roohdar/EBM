package com.billing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.billing.dto.ElectricityProviderDTO;
import com.billing.enums.PageView;
import com.billing.service.ElectricityProviderService;

@Controller
@RequestMapping(value = "/provider")
public class ElectricityProviderController {

	@Autowired
	private ElectricityProviderService electricityProviderService;

	@RequestMapping(value = "/registration",method = RequestMethod.GET)
	private String getElectricityProvider(ModelMap modelMap) {
		modelMap.put("electricityProviderDTO", electricityProviderService.getElectricityProvider());
		return PageView.ELECTRICITY_PROVIDER_REGISRATION_PAGE.getView();
	}


	@RequestMapping(value = "/registerProvider",method = RequestMethod.POST)
	private String registerElectricityProvider(@ModelAttribute("electricityProviderDTO") ElectricityProviderDTO electricityProviderDTO) {

		electricityProviderService.saveElectricityProvider(electricityProviderDTO);

		return PageView.ELECTRICITY_PROVIDER_REGISRATION_PAGE.getView();
	}

}
