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
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="guest")
public class Guest {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long guestId;
	
	@OneToOne(fetch = FetchType.LAZY)
    @MapsId
	private User user;

	@OneToMany(mappedBy="guest")
    private Set <Booking> bookings = new HashSet <Booking> ();
	
	@OneToMany(mappedBy="guest")
    private Set <Notification> notifications = new HashSet <Notification> ();
	
	public Guest() {
		
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
	
	public Set<Booking> getBookings() {
		return bookings;
	}

	public void addBooking(Booking booking) {
		this.bookings.add(booking);
	}
	
	public Set<Notification> getNotifications() {
		return notifications;
	}

	public void addNotification(Notification notification) {
		this.notifications.add(notification);
	}
}
