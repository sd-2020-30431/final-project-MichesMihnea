package com.in28minutes.springboot.web.service;

import java.text.ParseException;
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
import com.srccodes.beans.Photo;
import com.srccodes.beans.Review;
import com.srccodes.beans.User;
import com.srccodes.beans.Vehicle;

@Service
public class ShowVehicleService {

	@Autowired
	DBUtils utils;
	
	@Autowired
    private ApplicationEventPublisher applicationEventPublisher;
	
	public Vehicle getVehicle(Long vehicleId) {
		return utils.getVehicle(vehicleId);
	}
	
	public List <Photo> getPhotos(Vehicle vehicle){
		return utils.getPhotos(vehicle);
	}
	
	public Long getDiffInDays(String startDate, String endDate) throws ParseException {
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);  
		Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);  
		long startTime = date1.getTime();
		long endTime = date2.getTime();
		long diffTime = endTime - startTime;
		long diffDays = diffTime / (1000 * 60 * 60 * 24);
		return diffDays;
	}
	
	public Long addBooking(User user, Long vehicleId, String startDate, String endDate) throws ParseException{
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);  
		Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);  
		
		Booking booking = new Booking();
		Vehicle vehicle = utils.getVehicle(vehicleId);
		Guest guest = utils.getGuest(user);
		booking.setVehicle(vehicle);
		booking.setGuest(guest);
		booking.setStartDate(date1);
		booking.setEndDate(date2);
		if(vehicle.getOwner().getApproval().equals("automatic"))
			booking.setApproved(1);
		
		utils.addBooking(guest, vehicle, booking);
		return booking.getBookingId();
	}
	
	public void sendNotification(User user, Guest guest, Vehicle vehicle, Booking booking, Date startDate, Date endDate) {	
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String vehicleInfo = vehicle.getMake() + " " + vehicle.getModel() + " " + vehicle.getYear();
		NotificationEvent notificationEvent = new NotificationEvent(this, "Your vehicle " + vehicleInfo + " has a new booking" +
				" request from guest " + guest.getUser().getFirstName() + " " + guest.getUser().getLastName() + " " +
				"from : " + formatter.format(startDate) + " to " + formatter.format(endDate), user, Optional.of(guest), Optional.of(vehicle.getOwner()), Optional.of(vehicle),
				Optional.of(booking), Optional.empty());
		applicationEventPublisher.publishEvent(notificationEvent);
		
	}
	
	public Guest getGuest(User user) {
		return utils.getGuest(user);
	}
	
	public Booking getBooking(Long bookingId) {
		return utils.getBooking(bookingId);
	}
	
	public Float getVehicleAverage(Vehicle vehicle) {
		return utils.getVehicleAverage(vehicle);
	}
	
	public List <Review> getReviews(Vehicle vehicle){
		return utils.getReviews(vehicle);
	}
}


