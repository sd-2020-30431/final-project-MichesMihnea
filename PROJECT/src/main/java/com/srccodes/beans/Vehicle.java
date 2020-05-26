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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="vehicle")
public class Vehicle {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long vehicleId;

	@ManyToOne
    @JoinColumn(name ="FK_OwnerId")
    private Owner owner;
	
	@OneToMany(mappedBy="vehicle")
    private Set <Notification> notifications = new HashSet <Notification> ();
	
	@OneToMany(mappedBy="vehicle")
    private Set <Review> reviews = new HashSet <Review> ();
	
	@Column(name = "make")
	private String make;
	
	@Column(name = "model")
	private String model;
	
	@Column(name = "year")
	private String year;
	
	@Column(name = "power")
	private Integer power;
	
	@Column(name = "price")
	private Float price;
	
	@Column(name = "lat")
	private Float lat;
	
	@Column(name = "lang")
	private Float lng;
	
	@Column(name = "approved")
	private Integer approved;
	
	@Column(name = "available")
	private Integer available;
	
	@OneToMany(mappedBy="vehicle")
    private Set <Photo> photos = new HashSet <Photo> ();
	
	@OneToMany(mappedBy="vehicle")
    private Set <Booking> bookings = new HashSet <Booking> ();
 
	
	public Vehicle() {
		approved = 0;
		available = 1;
	}
	
	public Long getVehicleId() {
		return vehicleId;
	}
	
	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Owner getOwner() {
		return owner;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Set<Photo> getPhotos() {
		return photos;
	}

	public void addPhoto(Photo photo) {
		this.photos.add(photo);
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	public Float getLng() {
		return lng;
	}

	public void setLng(Float lng) {
		this.lng = lng;
	}

	public Integer getApproved() {
		return approved;
	}

	public void setApproved(Integer approved) {
		this.approved = approved;
	}

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
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
	
	public Set<Review> getReviews() {
		return reviews;
	}

	public void addReview(Review review) {
		this.reviews.add(review);
	}

}
