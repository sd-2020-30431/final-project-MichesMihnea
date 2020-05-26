package com.in28minutes.springboot.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in28minutes.springboot.web.DBUtils;

@Service
public class AboutService {
	
	@Autowired
	DBUtils utils;
	
	public Integer getTotalVehicles() {
		return utils.getTotalVehicles();
	}
	
	public Integer getTotalBookings() {
		return utils.getTotalBookings();
	}
	
	public Float getAveragePrice() {
		return utils.getAveragePrice();
	}
	
	public Integer getTotalOwners() {
		return utils.getTotalOwners();
	}
	
	public Integer getTotalReviews() {
		return utils.getTotalReviews();
	}

}
