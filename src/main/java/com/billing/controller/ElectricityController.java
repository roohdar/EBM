package com.billing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.billing.enums.PageView;

@Controller
@RequestMapping(value = "/")
public class ElectricityController {

	@RequestMapping(method = RequestMethod.GET)
	private String getIndex() {
		return PageView.INDEX.getView();
	}


}
