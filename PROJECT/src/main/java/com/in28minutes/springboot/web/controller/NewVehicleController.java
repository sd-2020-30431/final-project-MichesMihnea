package com.in28minutes.springboot.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.in28minutes.springboot.web.service.NewVehicleService;
import com.srccodes.beans.Car;
import com.srccodes.beans.User;

@Controller
@SessionAttributes("user")
public class NewVehicleController {
	
	@Autowired
	NewVehicleService service;
	
	@RequestMapping(value="/newVehicle", method = RequestMethod.GET)
	public String showNewVehiclePage(ModelMap model){
		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		service.setMakes(model);
		service.setProductionYears(model);
		service.setFuelTypes(model);
		
		return "newVehicle";
	}
	
	@RequestMapping(value="/newVehicle", method = RequestMethod.POST)
	public String addNewVehicle(ModelMap model, @RequestParam String makes, @RequestParam String models, @RequestParam String years,
			@RequestParam String fuels, @RequestParam String power, @RequestParam String price, @RequestParam String emissions,
			@RequestParam String seats, @RequestParam(defaultValue ="0") Float lat, @RequestParam(defaultValue = "0") Float lng, HttpServletRequest request,
			RedirectAttributes redirectAttributes){
		
		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		boolean error = service.validateInput(model, makes, models, years, fuels, power, price, emissions, seats, lat, lng);
		service.setMakes(model);
		service.setProductionYears(model);
		service.setFuelTypes(model);
		
		if(error)
			return "newVehicle";

		System.out.println("CALLING ADDCAR");
		Car car = service.addCar(user, makes, models, years, fuels, power, price, emissions, seats, lat, lng);
		
		try {
			service.uploadPhotos(request, car);
			service.sendNewVehicleNotification(user, service.getOwner(user), car);
		} catch (IOException | ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		redirectAttributes.addAttribute("successString", "Your new vehicle has been successfully created and is pending approval!");
		
		return "redirect:/myAccount";
	
	}

}
