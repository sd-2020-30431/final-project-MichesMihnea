package com.in28minutes.springboot.web.controller;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.in28minutes.springboot.web.service.LoginService;
import com.srccodes.beans.User;

@Controller
@SessionAttributes("user")
public class LoginController {
	
	@Autowired
	LoginService service;
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String showLoginPage(ModelMap model, @CookieValue(value = "userName", defaultValue="User") String rememberedUserName, @RequestParam(defaultValue = "") String successString){
		model.put("rememberedUserName", rememberedUserName);
		model.put("successString", successString);
		return "login";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String showWelcomePage(ModelMap model, @RequestParam String userName, @RequestParam String password, @RequestParam(defaultValue = "N") String rememberMe,
			HttpServletResponse response, @CookieValue(value="userName", defaultValue="User") String rememberedUserName){
		
		User user = service.validateUser(userName, password);

		model.put("rememberedUserName", rememberedUserName);
		
		if (user == null) {
			model.put("errorString", "Invalid Credentials");
			return "login";
		}
	    
		   
		if(rememberMe.equals("Y")) {
			response.addCookie(new Cookie("userName", user.getUserName()));
		}
		
		model.put("user", user);
		
		return "redirect:/myAccount";
	}

}
