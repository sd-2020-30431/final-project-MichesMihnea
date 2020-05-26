package com.in28minutes.springboot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.in28minutes.springboot.web.service.AboutService;

@Controller
public class AboutController {
	
	@Autowired
	AboutService service;

	@RequestMapping(value="/about", method = RequestMethod.GET)
	public String showAboutPage(ModelMap model){
		
		model.put("totalVehicles", service.getTotalVehicles());
		model.put("totalOwners", service.getTotalOwners());
		model.put("totalBookings", service.getTotalBookings());
		model.put("averagePrice", service.getAveragePrice());
		model.put("totalReviews", service.getTotalReviews());
		return "about";
	}
	
}
