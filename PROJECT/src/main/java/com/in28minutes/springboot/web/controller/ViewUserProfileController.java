package com.in28minutes.springboot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.in28minutes.springboot.web.service.ViewUserProfileService;
import com.srccodes.beans.User;

@Controller
@SessionAttributes("user")
public class ViewUserProfileController {
	
	@Autowired
	ViewUserProfileService service;

	@RequestMapping(value="/viewUserProfile", method = RequestMethod.GET)
	public String showUserProfilePage(ModelMap model, @RequestParam Long userId){
		
		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		User userProfile = service.getUser(userId);
		model.put("userProfile", user);
		model.put("fullName", user.getFirstName() + " " + user.getLastName());
		model.put("reviews", service.getReviews(userProfile));
		model.put("userScore", service.getUserAverage(userProfile));
		
		return "viewUserProfile";

	}
}