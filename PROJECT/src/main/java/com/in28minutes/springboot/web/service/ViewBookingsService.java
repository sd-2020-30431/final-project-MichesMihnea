package com.in28minutes.springboot.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in28minutes.springboot.web.DBUtils;
import com.srccodes.beans.Booking;
import com.srccodes.beans.User;
import com.srccodes.beans.Vehicle;

@Service
public class ViewBookingsService {

	@Autowired
	DBUtils utils;
	
	public List <Booking> getBookings(User user){
		if(utils.getGuest(user) != null)
			return utils.getBookings(utils.getGuest(user));
		else return utils.getBookings(utils.getOwner(user));
	}
	
	public List <Booking> getBookings(Long vehicleId){
		return utils.getBookings(utils.getVehicle(vehicleId));
	}
	
	public void startBooking(Long bookingId) {
		Booking booking = utils.getBooking(bookingId);
		booking.setStarted(1);
		utils.flush();
	}
	
	public void completeBooking(Long bookingId) {
		Booking booking = utils.getBooking(bookingId);
		booking.setComplete(1);
		utils.flush();
	}
}
