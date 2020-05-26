package com.in28minutes.springboot.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in28minutes.springboot.web.DBUtils;
import com.srccodes.beans.User;

@Service
public class ChangePasswordService {

	@Autowired
	DBUtils utils;
	
	public void updatePassword(User user, String password) {
		utils.updatePassword(user, password);
	}
	
	public boolean validatePassword(String password) {
		if(password.length() < 4)
			return false;
		
		if(password.length() > 30)
			return false;
		
		return true;
	}
}
