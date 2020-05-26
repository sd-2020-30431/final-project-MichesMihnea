package com.srccodes.beans;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="review")
public class Review {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long reviewId;

	@ManyToOne
    @JoinColumn(name ="FK_UserId")
    private User user;
	
	@ManyToOne
    @JoinColumn(name ="FK_ReceiverId")
    private User receiver;
	
	@ManyToOne
    @JoinColumn(name ="FK_VehicleId")
    private Vehicle vehicle;
	
	@ManyToOne
    @JoinColumn(name ="FK_BookingId")
    private Booking booking;
	
	@Column(name = "score")
	private Float score;
	
	@Column(name = "message")
	private String message;
	
	@OneToMany(mappedBy="review")
    private Set <Notification> notifications = new HashSet <Notification> ();
 
	public Review() {
		
	}

	public Long getReviewId() {
		return reviewId;
	}

	public User getReceiver() {
		return receiver;
	}


	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}


	public Float getScore() {
		return score;
	}


	public void setScore(Float score) {
		this.score = score;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Set<Notification> getNotifications() {
		return notifications;
	}

	public void addNotification(Notification notification) {
		this.notifications.add(notification);
	}

}
