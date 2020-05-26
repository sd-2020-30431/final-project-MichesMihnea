package com.in28minutes.springboot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.in28minutes.springboot.web.service.LoginService;
import com.in28minutes.springboot.web.service.MyAccountService;
import com.srccodes.beans.User;

@Controller
@SessionAttributes("user")
public class MyAccountController {
	
	@Autowired
	MyAccountService service;

	@RequestMapping(value="/myAccount", method = RequestMethod.GET)
	public String showMyAccountPage(ModelMap model, @RequestParam(defaultValue = "") String successString){
		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		model.put("successString", successString);
		model.put("countBookings", service.getBookingCount(user));
		model.put("countNotifications", service.getNotificationCount(user));
			
		return "myAccount";
	}
	
	@RequestMapping(value="/myAccount", method = RequestMethod.POST)
	public String LogOut(ModelMap model, SessionStatus status){
		status.setComplete();
		return "redirect:/login";
	}
	
}
