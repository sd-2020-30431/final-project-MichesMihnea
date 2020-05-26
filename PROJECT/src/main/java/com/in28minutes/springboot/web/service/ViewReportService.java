package com.in28minutes.springboot.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in28minutes.springboot.web.DBUtils;
import com.srccodes.beans.Review;

@Service
public class ViewReportService {
	
	@Autowired
	DBUtils utils;
	
	public Review getReview(Long reviewId) {
		return utils.getReview(reviewId);
	}
	
	public void deleteReview(Long reviewId) {
		utils.deleteReview(reviewId);
	}
	
	public void deleteNotification(Long notificationId) {
		utils.deleteNotification(notificationId);
	}

}
