package com.in28minutes.springboot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.in28minutes.springboot.web.service.ChangeEmailService;
import com.in28minutes.springboot.web.service.ChangePasswordService;
import com.srccodes.beans.User;

@Controller
@SessionAttributes("user")
public class ChangeEmailController {
	
	@Autowired
	ChangeEmailService service;

	@RequestMapping(value="/changeEmail", method = RequestMethod.GET)
	public String showChangePasswordPage(ModelMap model){
		User user = (User) model.get("user");
		if(user == null)
			return "redirect:/login";

		return "changeEmail";
	}
	
	@RequestMapping(value="/changeEmail", method = RequestMethod.POST)
	public String changePassword(ModelMap model, @RequestParam String newEmail, 
			@RequestParam String confirmEmail, RedirectAttributes redirectAttributes){
		
		User user = (User) model.get("user");
		boolean hasError = false;
		String errorString = "Error: ";
		
		if(!service.validateEmail(newEmail)) {
			hasError = true;
			errorString += "new email is invalid";
		}
		
		if(!newEmail.equals(confirmEmail)) {
			if(hasError) {
				errorString += ", ";
			}
			hasError = true;
			errorString += "emails don't match!";
		}
		
		if(!hasError) {
			service.updateEmail(user, newEmail);
			service.updateSession(model, user);
		}
		else {
			model.put("errorString", errorString);
			return "changeEmail";
		}
		
		redirectAttributes.addAttribute("successString", "Email changed successfully!");
		return "redirect:/controlPanel";
	}
}