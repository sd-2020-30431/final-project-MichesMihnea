package com.in28minutes.springboot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ContactController {

	@RequestMapping(value="/contact", method = RequestMethod.GET)
	public String showContactPage(ModelMap model){
		return "contact";
	}
	
}
