package com.in28minutes.springboot.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.in28minutes.springboot.web.service.ViewBookingsService;
import com.srccodes.beans.Booking;
import com.srccodes.beans.User;

@Controller
@SessionAttributes("user")
public class ViewBookingsController {

	@Autowired
	ViewBookingsService service;
	
	@RequestMapping(value="/viewBookings", method = RequestMethod.GET)
	public String showMyAccountPage(ModelMap model, @RequestParam(defaultValue = "0") Long vehicleId,
			@RequestParam(defaultValue = "0") Integer owner, @RequestParam(defaultValue = "") String successString){
		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		List <Booking> bookings = null;
		
		if(vehicleId == 0)
			bookings = service.getBookings(user);
		else
			bookings = service.getBookings(vehicleId);
		
		model.put("bookings", bookings);
		model.put("owner", owner);
		model.put("successString", successString);
			
		return "viewBookings";
	}
	
	@RequestMapping(value="/startBooking", method = RequestMethod.GET)
	public String startBooking(ModelMap model, @RequestParam(defaultValue = "0") Long vehicleId,
			@RequestParam(defaultValue = "0") Integer owner, RedirectAttributes redirectAttributes, 
			@RequestParam Long bookingId){
		
		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		redirectAttributes.addAttribute("owner", 1);
		redirectAttributes.addAttribute("vehicleId", vehicleId);
		redirectAttributes.addAttribute("successString", "Booking started successfully!");
		
		service.startBooking(bookingId);
		
		return "redirect:/viewBookings";
	}
	
	@RequestMapping(value="/completeBooking", method = RequestMethod.GET)
	public String completeBooking(ModelMap model, @RequestParam(defaultValue = "0") Long vehicleId,
			@RequestParam(defaultValue = "0") Integer owner, RedirectAttributes redirectAttributes, 
			@RequestParam Long bookingId){
		
		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		redirectAttributes.addAttribute("owner", 1);
		redirectAttributes.addAttribute("vehicleId", vehicleId);
		redirectAttributes.addAttribute("successString", "Booking completed successfully!");
		
		service.completeBooking(bookingId);
		
		return "redirect:/viewBookings";
	}
}
