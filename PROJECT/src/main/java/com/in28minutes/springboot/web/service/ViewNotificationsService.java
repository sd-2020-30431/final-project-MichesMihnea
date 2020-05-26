package com.in28minutes.springboot.web.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.in28minutes.springboot.web.DBUtils;
import com.srccodes.beans.Booking;
import com.srccodes.beans.Guest;
import com.srccodes.beans.Notification;
import com.srccodes.beans.Owner;
import com.srccodes.beans.Review;
import com.srccodes.beans.User;
import com.srccodes.beans.Vehicle;

@Service
public class ViewNotificationsService {

	@Autowired
	DBUtils utils;
	
	@Autowired
    private ApplicationEventPublisher applicationEventPublisher;
	
	public List <Notification> getNotifications(User user){
		return utils.getNotifications(user);
	}
	
	public void deleteNotification(Long notificationId, Integer denial) {
		Notification notification = utils.getNotification(notificationId);
		if(denial == 1) {
			this.sendBookingDenialNotification(notification.getGuest().getUser(), notification.getGuest(),
					notification.getVehicle(), notification.getBooking(), notification.getBooking().getStartDate(), 
					notification.getBooking().getEndDate());
			utils.deleteBooking(notification.getBooking());
			utils.flush();
		}
		utils.deleteNotification(notificationId);
	}
	
	public void approveBooking(Long notificationId) {
		Notification notification = utils.getNotification(notificationId);
		this.sendBookingApprovalNotification(notification.getGuest().getUser(), notification.getGuest(),
				notification.getVehicle(), notification.getBooking(), notification.getBooking().getStartDate(), 
				notification.getBooking().getEndDate());
		utils.approveBooking(notification);
	}
	
	public void deleteRequest(Long notificationId, Integer denial) {
		if(denial == 1) {
			Notification notification = utils.getNotification(notificationId);
			this.sendRequestDenialNotification(notification.getOwner().getUser(), notification.getOwner(),
					notification.getVehicle());
		}
		Notification notification = utils.getNotification(notificationId);
		utils.deleteVehicle(notification.getVehicle());
		utils.flush();
		utils.deleteNotification(notificationId);
	}
	
	public void approveRequest(Long notificationId) {
		Notification notification = utils.getNotification(notificationId);
		this.sendRequestApprovalNotification(notification.getOwner().getUser(), notification.getOwner(),
				notification.getVehicle());
		utils.approveVehicle(notification);
	}
	
	public void sendBookingApprovalNotification(User user, Guest guest, Vehicle vehicle, Booking booking, Date startDate, Date endDate) {	
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String vehicleInfo = vehicle.getMake() + " " + vehicle.getModel() + " " + vehicle.getYear();
		NotificationEvent notificationEvent = new NotificationEvent(this, "Your booking request for " + vehicleInfo + 
				" from : " + formatter.format(startDate) + " to " + formatter.format(endDate) + " has been approved!",
				user, Optional.of(guest), Optional.of(vehicle.getOwner()), Optional.of(vehicle),
				Optional.of(booking), Optional.empty());
		applicationEventPublisher.publishEvent(notificationEvent);
		
	}
	
	public void sendBookingDenialNotification(User user, Guest guest, Vehicle vehicle, Booking booking, Date startDate, Date endDate) {	
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String vehicleInfo = vehicle.getMake() + " " + vehicle.getModel() + " " + vehicle.getYear();
		NotificationEvent notificationEvent = new NotificationEvent(this, "Your booking request for " + vehicleInfo + 
				" from : " + formatter.format(startDate) + " to " + formatter.format(endDate) + " has been denied!",
				user, Optional.of(guest), Optional.of(vehicle.getOwner()), Optional.of(vehicle),
				Optional.empty(), Optional.empty());
		applicationEventPublisher.publishEvent(notificationEvent);
		
	}
	
	public void sendRequestApprovalNotification(User user, Owner owner, Vehicle vehicle) {	

		String vehicleInfo = vehicle.getMake() + " " + vehicle.getModel() + " " + vehicle.getYear();
		NotificationEvent notificationEvent = new NotificationEvent(this, "Your vehicle creation request for " + vehicleInfo + 
				 " has been approved!",
				user, Optional.empty(), Optional.of(owner), Optional.of(vehicle),
				Optional.empty(), Optional.empty());
		applicationEventPublisher.publishEvent(notificationEvent);
		
	}
	
	public void sendRequestDenialNotification(User user, Owner owner, Vehicle vehicle) {	

		String vehicleInfo = vehicle.getMake() + " " + vehicle.getModel() + " " + vehicle.getYear();
		NotificationEvent notificationEvent = new NotificationEvent(this, "Your vehicle creation request for " + vehicleInfo + 
				 " has been denied!",
				user, Optional.empty(), Optional.of(owner), Optional.empty(),
				Optional.empty(), Optional.empty());
		applicationEventPublisher.publishEvent(notificationEvent);
		
	}
	
	public void sendReviewReportNotification(User user, Long reviewId) {
		String userInfo = user.getFirstName() + " " + user.getLastName();
		NotificationEvent notificationEvent = new NotificationEvent(this, "User " + userInfo + 
				 " has reported a review.",
				utils.getAdmin(), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.of(utils.getReview(reviewId)));
		applicationEventPublisher.publishEvent(notificationEvent);
	}

}
