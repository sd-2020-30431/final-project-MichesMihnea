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

import com.in28minutes.springboot.web.service.NewReviewService;
import com.srccodes.beans.Booking;
import com.srccodes.beans.User;
import com.srccodes.beans.Vehicle;

@Controller
@SessionAttributes("user")
public class NewReviewController {
	
	@Autowired
	NewReviewService service;

	@RequestMapping(value="/newReview", method = RequestMethod.GET)
	public String showNewReviewPage(ModelMap model, @RequestParam Long bookingId){
		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		Booking booking = service.getBooking(bookingId);
		model.put("booking", booking);
		Vehicle vehicle = booking.getVehicle();
		String vehicleInfo = vehicle.getMake() + " " + vehicle.getModel() + " " + vehicle.getYear();
		model.put("vehicle", booking.getVehicle());
		if(user.getType().equals("guest"))
			model.put("vehicleInfo", vehicleInfo);
		else model.put("vehicleInfo", booking.getGuest().getUser().getFirstName() + " " + booking.getGuest().getUser().getLastName());
			
		return "newReview";
	}
	
	@RequestMapping(value="/newReview", method = RequestMethod.POST)
	public String addNewReview(ModelMap model, @RequestParam Long bookingId, @RequestParam Float score,
			@RequestParam String comment, RedirectAttributes redirectAttributes){
		
		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		redirectAttributes.addAttribute("successString", "Review created successfully!");
		
		service.addNewReview(user, service.getBooking(bookingId), score, comment);
		
		return "redirect:/myAccount";
	}
}
