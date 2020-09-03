package com.billing.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.billing.dto.ElectricityMeterReadingDTO;
import com.billing.dto.ElectricityProviderDTO;
import com.billing.dto.ElectricityUserDTO;
import com.billing.entity.ElectricMeter;
import com.billing.entity.ElectricityMeterReading;
import com.billing.enums.PageView;
import com.billing.service.ElectricMeterService;
import com.billing.service.ElectricityMeterReadingService;
import com.billing.service.ElectricityProviderService;
import com.billing.service.ElectricityUserService;
import com.billing.service.EncryptDecryptUtils;

@Controller
@RequestMapping(value = "/provider")
public class ElectricityProviderController {

	@Autowired
	private ElectricityUserService electricityUserService;

	@Autowired
	private ElectricityProviderService electricityProviderService;

	@Autowired
	private ElectricMeterService electricMeterService;

	@Autowired
	ElectricityMeterReadingService electricityMeterReadingService;

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


	@RequestMapping(value = "/addMeterReading/{meterNumber}",method = RequestMethod.GET)
	private String addMeterReading(ModelMap modelMap, @PathVariable("meterNumber") String meterNumber) {

		ElectricMeter electricMeter = electricMeterService.findByMeterNumber(meterNumber);
		ElectricityMeterReadingDTO electricityMeterReading = new ElectricityMeterReadingDTO();
		electricityMeterReading.setMeterencryptedId(EncryptDecryptUtils.encrypt(String.valueOf(electricMeter.getId())));
		modelMap.put("meterNumber", electricMeter.getMeterNumber());
		modelMap.put("meterReading", electricityMeterReading);
		modelMap.put("electricityUserDTO",electricityUserService.setToDTO(electricMeter.getElectricityUser()));
		modelMap.put("meterReadings", electricityMeterReadingService.getAllReadingsByMeterNumber(electricMeter.getMeterNumber()));

		return PageView.ELECTRICITY_PROVIDER_USER_METER_PAGE.getView();
	}

	@RequestMapping(value = "/saveMeterReading",method = RequestMethod.POST)
	private String saveMeterReading(ModelMap modelMap, HttpServletRequest request) {


		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = df.parse(request.getParameter("startDate"));
			endDate = df.parse(request.getParameter("startDate"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ElectricityMeterReadingDTO dto = new ElectricityMeterReadingDTO();
		dto.setUnit(request.getParameter("unit"));
		dto.setMeterencryptedId(request.getParameter("meterencryptedId"));
		dto.setStartDate(startDate);
		dto.setEndDate(endDate);

		ElectricityMeterReading electricityMeterReading = electricityMeterReadingService.saveMeterReading(dto);
		return "redirect:/provider/addMeterReading/"+electricityMeterReading.getElectricMeter().getMeterNumber();
	}

	@ResponseBody
	@RequestMapping(value = "/getYearlyElectricityConsumptionReport/{year}/{meterNumber}",method = RequestMethod.GET)
	private Map<String, Object> getYearlyElectricityConsumptionReport(@PathVariable("year") String year, @PathVariable("meterNumber") String meterNumber) {

		Map<String, Object> data = new HashMap<String, Object>();

		ElectricMeter electricMeter = electricMeterService.findByMeterNumber(meterNumber);

		List<ElectricityMeterReadingDTO> electricityMeterReadingDTOs = electricityMeterReadingService.getAllReadingsByMeterNumberAndYear(electricMeter.getMeterNumber(), year);
		Double average = electricityMeterReadingDTOs.stream() .mapToDouble(mr -> Double.parseDouble(mr.getUnit())).average().getAsDouble();

		data.put("electricityMeterReadingDTOs", electricityMeterReadingDTOs);
		data.put("average", average);
		data.put("count", electricityMeterReadingDTOs.size());

		return data;

	}

}
