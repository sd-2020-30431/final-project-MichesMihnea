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

import com.in28minutes.springboot.web.service.MyVehicleService;
import com.in28minutes.springboot.web.service.SearchVehiclesService;
import com.srccodes.beans.Photo;
import com.srccodes.beans.User;
import com.srccodes.beans.Vehicle;

@Controller
@SessionAttributes("user")
public class SearchVehiclesController {
	
	@Autowired
	SearchVehiclesService service;
	
	@RequestMapping(value="/searchVehicles", method = RequestMethod.GET)
	public String showNewVehiclePage(ModelMap model, @RequestParam(defaultValue = "50") Integer range, @RequestParam String startDate,
			@RequestParam String endDate, @RequestParam String msg, @RequestParam(defaultValue = "0") Float geoLocationLat,
			@RequestParam(defaultValue = "0") Float geoLocationLng){

		User user = (User) model.get("user");
		
		Date startDateConv = null;
		Date endDateConv = null;
		try {
			startDateConv = new SimpleDateFormat("MM/dd/yyyy").parse(startDate);
			endDateConv = new SimpleDateFormat("MM/dd/yyyy").parse(endDate); 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		System.out.println("MESSAGE IS: " + msg);
		
		
		if(user == null)
			return "redirect:/login";
		
		List <Vehicle> rangeVehicles = service.getVehiclesInRange(range, geoLocationLat, geoLocationLng);
		List <Photo> photos = service.getPhotoList(rangeVehicles);
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		model.put("range", range);
		model.put("startDate", formatter.format(startDateConv));
		model.put("endDate", formatter.format(endDateConv));
		model.put("rangeVehicles", rangeVehicles);
		model.put("photos", photos);

		return "searchVehicles";
	}

}
