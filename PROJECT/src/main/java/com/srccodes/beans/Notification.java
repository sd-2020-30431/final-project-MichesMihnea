package com.srccodes.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Notification {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long notificationId;
	
	@ManyToOne
    @JoinColumn(name ="FK_UserId")
    private User user;
	
	@ManyToOne
    @JoinColumn(name ="FK_VehicleId")
    private Vehicle vehicle;
	
	@ManyToOne
    @JoinColumn(name ="FK_GuestId")
    private Guest guest;

	@ManyToOne
    @JoinColumn(name ="FK_OwnerId")
    private Owner owner;
	
	@ManyToOne
    @JoinColumn(name ="FK_BookingId")
    private Booking booking;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "type")
	private String type;

	@ManyToOne
    @JoinColumn(name ="FK_ReviewId")
    private Review review;
	
	public Notification() {
		
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getNotificationId() {
		return notificationId;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
