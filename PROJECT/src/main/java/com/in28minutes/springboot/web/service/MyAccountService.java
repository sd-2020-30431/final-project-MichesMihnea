package com.in28minutes.springboot.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in28minutes.springboot.web.DBUtils;
import com.srccodes.beans.Guest;
import com.srccodes.beans.User;

@Service
public class MyAccountService {

	@Autowired
	DBUtils utils;
	
	public Integer getBookingCount(User user) {
		Guest guest = utils.getGuest(user);
		if(guest != null)
			return utils.getBookings(guest).size();
		
		return 0;
	}
	
	public Integer getNotificationCount(User user) {
		return utils.getNotifications(user).size();
	}
}
