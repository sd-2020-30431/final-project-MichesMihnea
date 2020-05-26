package com.in28minutes.springboot.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.in28minutes.springboot.web.service.CreateAccountService;
import com.srccodes.beans.User;

@Controller
public class CreateAccountController {
	
	@Autowired
	CreateAccountService service;

	@RequestMapping(value="/createAccount", method = RequestMethod.GET)
	public String showMyAccountPage(ModelMap model){
		return "createAccount";
	}
	
	@RequestMapping(value="/createAccount", method = RequestMethod.POST)
	public String LogOut(ModelMap model, @RequestParam String userName, @RequestParam String password, @RequestParam String email, 
			@RequestParam String firstName, @RequestParam String lastName, @RequestParam String userType,
			RedirectAttributes redirectAttributes){
		
		String errorString = service.validateUser(userName, password, email, firstName, lastName);
		
		service.addAttributes(userName, password, email, firstName, lastName, model);
		
		if(errorString != null) {
			model.put("errorString", errorString);
			return "createAccount";
		}
		
		service.addNewUser(userName, password, email, firstName, lastName, userType);
		redirectAttributes.addAttribute("successString", "Account successfully created!");
		
		return "redirect:/login";
	}
	
}
