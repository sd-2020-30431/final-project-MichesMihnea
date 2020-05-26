package com.in28minutes.springboot.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in28minutes.springboot.web.DBUtils;
import com.srccodes.beans.Review;
import com.srccodes.beans.User;

@Service
public class ViewUserProfileService {
	
	@Autowired
	DBUtils utils;

	public User getUser(Long userId) {
		return utils.getUser(userId);
	}
	
	public List <Review> getReviews(User user){
		return utils.getReviews(user);
	}
	
	public Float getUserAverage(User user) {
		return utils.getUserAverage(user);
	}
}
