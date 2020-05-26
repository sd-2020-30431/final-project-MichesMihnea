package com.in28minutes.springboot.web.service;

import java.util.Optional;

import org.springframework.context.ApplicationEvent;

import com.in28minutes.springboot.web.DBUtils;
import com.srccodes.beans.Booking;
import com.srccodes.beans.Guest;
import com.srccodes.beans.Notification;
import com.srccodes.beans.Owner;
import com.srccodes.beans.Review;
import com.srccodes.beans.User;
import com.srccodes.beans.Vehicle;

public class NotificationEvent extends ApplicationEvent{
	private String message;
	private User user;
	private Guest guest = null;
	private Owner owner = null;
	private Vehicle vehicle = null;
	private Booking booking = null;
	private Review review = null;

	 
    public NotificationEvent(Object source, String message, User user, Optional <Guest> guest, Optional <Owner> owner,
    		Optional <Vehicle> vehicle, Optional <Booking> booking, Optional <Review> review) {
        super(source);     
        this.message = message;
        this.user = user;
        
        if(guest.isPresent()) {
        	this.guest = guest.get();
        }
        
        if(owner.isPresent()) {
        	this.owner = owner.get();
        }
        
        if(vehicle.isPresent()) {
        	this.vehicle = vehicle.get();
        }
        
        if(booking.isPresent()) {
        	this.booking = booking.get();
        }
        
        if(review.isPresent()) {
        	this.review = review.get();
        }
    }


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Guest getGuest() {
		return guest;
	}


	public void setGuest(Guest guest) {
		this.guest = guest;
	}


	public Owner getOwner() {
		return owner;
	}


	public void setOwner(Owner owner) {
		this.owner = owner;
	}


	public Vehicle getVehicle() {
		return vehicle;
	}


	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}


	public Booking getBooking() {
		return booking;
	}


	public void setBooking(Booking booking) {
		this.booking = booking;
	}


	public Review getReview() {
		return review;
	}


	public void setReview(Review review) {
		this.review = review;
	}


}
