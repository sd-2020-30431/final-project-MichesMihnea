package com.in28minutes.springboot.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.in28minutes.springboot.web.service.ShowVehicleService;
import com.srccodes.beans.Booking;
import com.srccodes.beans.Photo;
import com.srccodes.beans.User;
import com.srccodes.beans.Vehicle;

@Controller
@SessionAttributes("user")
public class ShowVehicleController {

	@Autowired
	ShowVehicleService service;
	
	@RequestMapping(value="/showVehicle", method = RequestMethod.GET)
	public String showVehiclePage(ModelMap model, @RequestParam Long vehicleId, @RequestParam(defaultValue = "") String startDate,
			@RequestParam(defaultValue = "") String endDate, @RequestParam(defaultValue = "") String errorString,
			@RequestParam(defaultValue = "0") Long bookingId, @RequestParam(defaultValue = "0") Integer search
			){
		
		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		Vehicle vehicle = service.getVehicle(vehicleId);
		List <Photo> photos = service.getPhotos(vehicle);
		String vehicleInfo = vehicle.getMake() + " " + vehicle.getModel() + " " + vehicle.getYear();
		model.put("vehicle", vehicle);
		model.put("photos", photos);
		model.put("reviews", service.getReviews(vehicle));
		model.put("vehicleScore", service.getVehicleAverage(vehicle));
		if(bookingId == 0) {
			model.put("startDate", startDate);
			model.put("endDate", endDate);
		}
		else {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Booking booking = service.getBooking(bookingId);
			startDate = formatter.format(booking.getStartDate());
			endDate = formatter.format(booking.getEndDate());
			model.put("startDate", startDate);
			model.put("endDate", endDate);
			if(booking.getApproved() == 0)
				model.put("unconfirmedString", "This booking has not yet been approved!");
			else model.put("successString", "This booking is confirmed!");
		}
		model.put("vehicleInfo", vehicleInfo);
		model.put("errorString", errorString);
		try {
			model.put("totalPrice", vehicle.getPrice() * service.getDiffInDays(startDate, endDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		model.put("search", search);

		return "showVehicle";
	}
	
	@RequestMapping(value="/showVehicle", method = RequestMethod.POST)
	public String createBooking(ModelMap model, @RequestParam Long vehicleId, @RequestParam String startDate,
			@RequestParam String endDate, RedirectAttributes redirectAttributes){
		
		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		if(!user.getType().equals("guest")) {
			redirectAttributes.addAttribute("errorString", "You can only book using a guest account!");
			redirectAttributes.addAttribute("startDate", startDate);
			redirectAttributes.addAttribute("endDate", endDate);
			redirectAttributes.addAttribute("vehicleId", vehicleId);
			
			return "redirect:/showVehicle";
		}
		
		Date startDateConv = null;
		Date endDateConv = null;
		
		try {
			startDateConv = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
			endDateConv = new SimpleDateFormat("dd/MM/yyyy").parse(endDate); 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Long bookingId = service.addBooking(user, vehicleId, startDate, endDate);
			service.sendNotification(service.getVehicle(vehicleId).getOwner().getUser(), service.getGuest(user), 
					service.getVehicle(vehicleId), service.getBooking(bookingId),
					startDateConv, endDateConv);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		redirectAttributes.addAttribute("successString", "Booking created successfully!");
		
		return "redirect:/myAccount";
	}
}
