package com.in28minutes.springboot.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.in28minutes.springboot.web.service.ViewVehiclesService;
import com.srccodes.beans.User;
import com.srccodes.beans.Vehicle;

@Controller
@SessionAttributes("user")
public class ViewVehiclesController {
	
	@Autowired
	ViewVehiclesService service;
	
	@RequestMapping(value="/viewVehicles", method = RequestMethod.GET)
	public String showNewVehiclePage(ModelMap model){

		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		List <Vehicle> vehicles = service.getVehicles(user);
		model.put("vehicles", vehicles);

		return "viewVehicles";
	}
}
