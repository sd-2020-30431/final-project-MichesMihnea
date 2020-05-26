package com.in28minutes.springboot.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.in28minutes.springboot.web.DBUtils;
import com.repos.UserRepository;
import com.srccodes.beans.User;

@Service
public class LoginService {
	
	@Autowired
	DBUtils utils;

	public User validateUser(String userName, String password) {

		return utils.findUser(userName, password);
	}

}
