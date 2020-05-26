package com.in28minutes.springboot.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.in28minutes.springboot.web.service.IndexService;
import com.in28minutes.springboot.web.service.LoginService;

@Controller
@SessionAttributes({"name", "msg"})
public class IndexController {
	
	@Autowired
	IndexService service;
	
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String showIndexPage(ModelMap model){
		return "index";
	}
	
	@RequestMapping(value="/index.html", method = RequestMethod.GET)
	public String redirectIndexPage(ModelMap model){
		return "redirect:/index";
	}
	
	@RequestMapping(value="/index", method = RequestMethod.POST)
	public String searchVehicles(ModelMap model ,@RequestParam String checkin_date, @RequestParam String checkout_date,
			@RequestParam Integer seats, RedirectAttributes redirectAttributes, @RequestParam(defaultValue = "0") Float geoLocationLat,
			@RequestParam(defaultValue = "0") Float geoLocationLng, @RequestParam String range) {
		
		String msg = (String) model.get("msg");
		
		boolean hasError = service.validateInput(model, range, checkin_date, checkout_date, seats, geoLocationLat, geoLocationLng);
		
		if(hasError) {
			return "index";
		}
		 
		System.out.println("IN INDEX POST: " + geoLocationLat + " " + geoLocationLng);
		redirectAttributes.addAttribute("range", range);
		redirectAttributes.addAttribute("msg", msg);
		redirectAttributes.addAttribute("startDate", checkin_date);
		redirectAttributes.addAttribute("endDate", checkout_date);
		redirectAttributes.addAttribute("seats", seats);
		redirectAttributes.addAttribute("geoLocationLat", geoLocationLat);
		redirectAttributes.addAttribute("geoLocationLng", geoLocationLng);
		return "redirect:/searchVehicles";
	}
	
}
