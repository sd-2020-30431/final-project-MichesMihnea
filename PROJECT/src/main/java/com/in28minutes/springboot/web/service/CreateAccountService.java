package com.in28minutes.springboot.web.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.in28minutes.springboot.web.DBUtils;
import com.srccodes.beans.User;


@Service
public class CreateAccountService {

	@Autowired
	DBUtils utils;
	
	public boolean validateUserName(String userName) {
		if(userName.length() < 1)
			return false;
		
		if(userName.length() > 30)
			return false;
		
		return true;
	}
	
	public boolean validatePassword(String password) {
		if(password.length() < 4)
			return false;
		
		if(password.length() > 30)
			return false;
		
		return true;
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
	
	public boolean validateName(String name) {
		if(name.length() < 1)
			return false;
		
		if(name.length() > 30)
			return false;
		
		return true;
	}
	
	public String validateUser(String userName, String password, String email, String firstName, String lastName) {
		String errorString = "Invalid input! Check: ";
		boolean hasError = false;
		
		if(this.validateUserName(userName) == false) {
			errorString += "username";
			hasError = true;
		}
		
		if(this.validatePassword(password) == false) {
			if(hasError)
				errorString += ", ";
			errorString += "password";
			hasError = true;
		}
		
		if(this.validateEmail(email) == false) {
			if(hasError)
				errorString += ", ";
			errorString += "email";
			hasError = true;
		}
		
		if(this.validateName(firstName) == false) {
			if(hasError)
				errorString += ", ";
			errorString += "first name";
			hasError = true;
		}
		
		if(this.validateName(lastName) == false) {
			if(hasError)
				errorString += ", ";
			errorString += "last name";
			hasError = true;
		}
		
		if(hasError)
			return errorString;
		else return null;
	}
	
	public void addAttributes(String userName, String password, String email, String firstName, String lastName, ModelMap model) {
		model.put("userName", userName);
		model.put("password", password);
		model.put("email", email);
		model.put("firstName", firstName);
		model.put("lastName", lastName);
	}
	
	public void addNewUser(String userName, String password, String email, String firstName, String lastName, String userType) {
		User user = new User();
		
		user.setUserName(userName);
		user.setPassword(password);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setType(userType);
		
		utils.addUser(user);
	}
}
