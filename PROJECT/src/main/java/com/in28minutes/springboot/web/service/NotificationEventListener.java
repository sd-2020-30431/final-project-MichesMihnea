package com.in28minutes.springboot.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.in28minutes.springboot.web.DBUtils;
import com.srccodes.beans.Notification;

@Component
public class NotificationEventListener implements ApplicationListener <NotificationEvent> {
	
	@Autowired
	DBUtils utils;
	
    @Override
    public void onApplicationEvent(NotificationEvent event) {

        Notification notification = new Notification();
        notification.setMessage(event.getMessage());
        notification.setUser(event.getUser());
        notification.setOwner(event.getOwner());
        notification.setGuest(event.getGuest());
        notification.setVehicle(event.getVehicle());
        notification.setBooking(event.getBooking());
        notification.setReview(event.getReview());
        
        if(event.getOwner() != null)
        	event.getOwner().addNotification(notification);
        
        if(event.getGuest() != null)
        	event.getGuest().addNotification(notification);
        
        if(event.getVehicle() != null)
        	event.getVehicle().addNotification(notification);
        
        if(event.getBooking() != null)
        	event.getBooking().addNotification(notification);
        
        if(event.getReview() != null)
        	event.getReview().addNotification(notification);
        
        if(event.getOwner() != null) {
	        if(event.getOwner().getUser().getUserId() == event.getUser().getUserId()) {
	        	if(event.getOwner().getApproval().equals("automatic"))
	        		notification.setType("bookingRequestAuto");
	        	else notification.setType("bookingRequestManual");
	        }
	     if(event.getMessage().contains("Your vehicle creation request")) {
	    	 notification.setType("vehicleCreationRequestUpdate");
	     }
	     
        }
        
        if(event.getGuest() != null)
	        if(event.getGuest().getUser().getUserId() == event.getUser().getUserId()) {
	        	notification.setType("info");
	        }
        
        if(event.getUser().getType().equals("admin")) {
        	notification.setType("vehicleApproval");
        }
        
        if(event.getMessage().contains("has reported"))
	    	 notification.setType("reviewReport");
        
        utils.addNotification(notification);
        utils.flush();
        
        System.out.println("EVENT HANDLED! New notification for user: " + notification.getUser().getFirstName());
    }
}