package com.in28minutes.springboot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.in28minutes.springboot.web.service.ChangePasswordService;
import com.in28minutes.springboot.web.service.ControlPanelService;
import com.srccodes.beans.User;

@Controller
@SessionAttributes("user")
public class ChangePasswordController {
	
	@Autowired
	ChangePasswordService service;

	@RequestMapping(value="/changePassword", method = RequestMethod.GET)
	public String showChangePasswordPage(ModelMap model){
		User user = (User) model.get("user");
		if(user == null)
			return "redirect:/login";

		return "changePassword";
	}
	
	@RequestMapping(value="/changePassword", method = RequestMethod.POST)
	public String changePassword(ModelMap model, @RequestParam String currentPassword, @RequestParam String newPassword, 
			@RequestParam String confirmPassword, RedirectAttributes redirectAttributes){
		
		User user = (User) model.get("user");
		boolean hasError = false;
		String errorString = "Error: ";
		
		if(!user.getPassword().equals(currentPassword)) {
			hasError = true;
			errorString += "current password is incorrect";
		}
		
		if(!newPassword.equals(confirmPassword)) {
			if(hasError) {
				errorString += ", ";
			}
			hasError = true;
			errorString += "passwords don't match!";
		}
		
		if(!service.validatePassword(newPassword)) {
			if(hasError) {
				errorString += ", ";
			}
			hasError = true;
			errorString += "password requirements not met";
		}
		
		if(!hasError) {
			service.updatePassword(user, newPassword);
		}
		else {
			model.put("errorString", errorString);
			return "changePassword";
		}
		
		redirectAttributes.addAttribute("successString", "Password changed successfully!");
		return "redirect:/controlPanel";
	}
}
