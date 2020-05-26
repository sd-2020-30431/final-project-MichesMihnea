package com.in28minutes.springboot.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.in28minutes.springboot.web.service.MyVehicleService;
import com.in28minutes.springboot.web.service.ViewVehiclesService;
import com.srccodes.beans.Photo;
import com.srccodes.beans.User;
import com.srccodes.beans.Vehicle;

@Controller
@SessionAttributes("user")
public class MyVehicleController {
	
	@Autowired
	MyVehicleService service;
	
	@RequestMapping(value="/myVehicle", method = RequestMethod.GET)
	public String showNewVehiclePage(ModelMap model, @RequestParam Long vehicleId, @RequestParam(defaultValue = "") String successString){

		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		Vehicle vehicle = service.getVehicle(vehicleId);
		List <Photo> vehicles = service.getPhotos(vehicle);
		model.put("photos", vehicles);
		model.put("successString", successString);
		model.put("vehicle", vehicle);
		model.put("reviews", service.getReviews(vehicle));
		model.put("vehicleScore", service.getVehicleAverage(vehicle));

		return "myVehicle";
	}
	
	@RequestMapping(value="/myVehicle", method = RequestMethod.POST)
	public String addNewPhotos(ModelMap model, @RequestParam Long vehicleId, HttpServletRequest request, RedirectAttributes redirectAttributes){
		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		Vehicle vehicle = service.getVehicle(vehicleId);
		
		try {
			service.uploadPhotos(request, vehicle);
		} catch (IOException | ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		redirectAttributes.addAttribute("vehicleId", vehicleId);
		redirectAttributes.addAttribute("successString", "Photo upload successful!");
		
		return "redirect:/myVehicle";
	}
	
	@RequestMapping(value="/changeAvailability", method = RequestMethod.GET)
	public String changeAvailability(ModelMap model, @RequestParam Long vehicleId, @RequestParam Integer available, RedirectAttributes redirectAttributes){
		
		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		Vehicle vehicle = service.getVehicle(vehicleId);
		redirectAttributes.addAttribute("vehicleId", vehicleId);
		service.updateAvailability(vehicle, available);
		
		return "redirect:/myVehicle";
	}
}
