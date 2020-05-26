package com.in28minutes.springboot.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in28minutes.springboot.web.DBUtils;
import com.srccodes.beans.User;
import com.srccodes.beans.Vehicle;

@Service
public class ViewVehiclesService {
	
	@Autowired
	DBUtils utils;
	
	public List<Vehicle> getVehicles(User user) {
		return utils.getVehicles(user);
	}

}
