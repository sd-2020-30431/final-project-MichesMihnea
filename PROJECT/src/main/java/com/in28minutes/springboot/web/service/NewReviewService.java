package com.in28minutes.springboot.web.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.in28minutes.springboot.web.DBUtils;
import com.srccodes.beans.Booking;
import com.srccodes.beans.Review;
import com.srccodes.beans.User;
import com.srccodes.beans.Vehicle;

@Service
public class NewReviewService {

	@Autowired
	DBUtils utils;
	
	@Autowired
    private ApplicationEventPublisher applicationEventPublisher;
	
	public Booking getBooking(Long bookingId) {
		return utils.getBooking(bookingId);
	}
	
	public void addNewReview(User user, Booking booking, Float score, String message) {
		
		Review review = new Review();
		review.setBooking(booking);
		review.setMessage(message);
		if(user.getType().equals("owner")) {
			
			review.setReceiver(booking.getGuest().getUser());
		}
		else review.setReceiver(booking.getVehicle().getOwner().getUser());
		review.setScore(score);
		review.setVehicle(booking.getVehicle());
		review.setUser(user);
		utils.addReview(review);
		review.getReceiver().addReview(review);
		this.sendNewReviewNotification(user, review.getReceiver(), booking);
		utils.flush();
	}
	
	public void sendNewReviewNotification(User user, User receiver, Booking booking) {
		Vehicle vehicle = booking.getVehicle();
		String vehicleInfo = vehicle.getMake() + " " + vehicle.getModel() + " " + vehicle.getYear();
		NotificationEvent notificationEvent = null;
		
		if(user.getType().equals("guest")) {
			notificationEvent = new NotificationEvent(this, "Your vehicle " + vehicleInfo + 
					 " has received a new review from " + user.getFirstName() + " " + user.getLastName() + ".",
					receiver, Optional.empty(), Optional.empty(), Optional.of(vehicle),
					Optional.of(booking), Optional.empty());
		}else {
			notificationEvent = new NotificationEvent(this, "You have received a new review from "
					+ user.getFirstName() + " " + user.getLastName() + ".",
					receiver, Optional.empty(), Optional.empty(), Optional.of(vehicle),
					Optional.of(booking), Optional.empty());
		}
		
		applicationEventPublisher.publishEvent(notificationEvent);
	}
}
