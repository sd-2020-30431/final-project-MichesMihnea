package com.srccodes.beans;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Booking {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long bookingId;
	
	@ManyToOne
    @JoinColumn(name ="FK_GuestId")
    private Guest guest;
	
	@ManyToOne
    @JoinColumn(name ="FK_VehicleId")
    private Vehicle vehicle;
	
	@Column(name = "startDate")
	private Date startDate;
	
	@Column(name = "endDate")
	private Date endDate;
	
	@Column(name = "approved")
	private Integer approved;
	
	@Column(name = "complete")
	private Integer complete;
	
	@Column(name = "started")
	private Integer started;
	
	@OneToMany(mappedBy="booking")
    private Set <Notification> notifications = new HashSet <Notification> ();
	
	@OneToMany(mappedBy="booking")
    private Set <Review> reviews = new HashSet <Review> ();
	
	public Booking() {
		approved = 0;
		complete = 0;
		started = 0;
	}

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getApproved() {
		return approved;
	}

	public void setApproved(Integer approved) {
		this.approved = approved;
	}

	public Long getBookingId() {
		return bookingId;
	}
	
	public Set<Notification> getNotifications() {
		return notifications;
	}

	public void addNotification(Notification notification) {
		this.notifications.add(notification);
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void addReview(Review review) {
		this.reviews.add(review);
	}

	public Integer getComplete() {
		return complete;
	}

	public void setComplete(Integer complete) {
		this.complete = complete;
	}

	public Integer getStarted() {
		return started;
	}

	public void setStarted(Integer started) {
		this.started = started;
	}
}
