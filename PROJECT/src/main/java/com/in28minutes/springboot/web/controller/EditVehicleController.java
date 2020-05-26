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

import com.in28minutes.springboot.web.service.EditVehicleService;
import com.in28minutes.springboot.web.service.MyVehicleService;
import com.srccodes.beans.Photo;
import com.srccodes.beans.User;
import com.srccodes.beans.Vehicle;

@Controller
@SessionAttributes("user")
public class EditVehicleController {

	@Autowired
	EditVehicleService service;
	
	@RequestMapping(value="/editVehicle", method = RequestMethod.GET)
	public String showNewVehiclePage(ModelMap model, @RequestParam Long vehicleId, @RequestParam(defaultValue = "") String successString){

		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		Vehicle vehicle = service.getVehicle(vehicleId);
		List <Photo> vehicles = service.getPhotos(vehicle);
		model.put("photos", vehicles);
		model.put("vehicle", vehicle);
		model.put("successString", successString);

		return "editVehicle";
	}
	
	@RequestMapping(value="/deletePhoto", method = RequestMethod.GET)
	public String deletePhoto(ModelMap model, @RequestParam Long vehicleId, @RequestParam Long photoId, RedirectAttributes redirectAttributes){
		
		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		service.deletePhoto(photoId);
		redirectAttributes.addAttribute("vehicleId", vehicleId);
		redirectAttributes.addAttribute("successString", "Photo deleted successfully!");
		
		return "redirect:/editVehicle";
	}
	
	@RequestMapping(value="/editVehicle", method = RequestMethod.POST)
	public String addNewPhotos(ModelMap model, @RequestParam Long vehicleId, @RequestParam Float newPrice, RedirectAttributes redirectAttributes,
			@RequestParam(defaultValue = "0") Float lat, @RequestParam(defaultValue = "0") Float lng){
		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		Vehicle vehicle = service.getVehicle(vehicleId);
		
		service.updatePrice(vehicle, newPrice);

		redirectAttributes.addAttribute("vehicleId", vehicleId);
		redirectAttributes.addAttribute("successString", "Price changed successfully!");
		
		if(lat != 0 && lng != 0) {
			service.updateCoords(vehicle, lat, lng);
			redirectAttributes.addAttribute("successString", "Price and coords changed successfully!");
		}
		
		return "redirect:/myVehicle";
	}
}
