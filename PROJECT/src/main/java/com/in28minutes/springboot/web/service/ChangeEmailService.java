package com.in28minutes.springboot.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.in28minutes.springboot.web.DBUtils;
import com.srccodes.beans.User;

@Service
public class ChangeEmailService {

	@Autowired
	DBUtils utils;
	
	public void updateEmail(User user, String email) {
		utils.updateEmail(user, email);
	}
	
	public boolean validateEmail(String email) {
		if(email.length() < 4)
			return false;
		
		if(email.length() > 30)
			return false;
		
		if(!email.contains("@"))
			return false;
		
		if(!email.contains("."))
			return false;
		
		return true;
	}
	
	public void updateSession(ModelMap model, User user) {
		utils.updateSession(model, user);
	}
}
