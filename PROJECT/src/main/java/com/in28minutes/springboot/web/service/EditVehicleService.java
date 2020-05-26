package com.in28minutes.springboot.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in28minutes.springboot.web.DBUtils;
import com.srccodes.beans.Photo;
import com.srccodes.beans.Vehicle;

@Service
public class EditVehicleService {

	@Autowired
	DBUtils utils;
	
	public List<Photo> getPhotos(Vehicle vehicle){
		return utils.getPhotos(vehicle);
	}
	
	public Vehicle getVehicle(Long vehicleId) {
		return utils.getVehicle(vehicleId);
	}
	
	public void deletePhoto(Long photoId) {
		utils.deletePhoto(photoId);
	}
	
	public void updatePrice(Vehicle vehicle, Float price) {
		utils.updatePrice(vehicle, price);
	}
	
	public void updateCoords(Vehicle vehicle, Float lat, Float lng) {
		utils.updateCoords(vehicle, lat, lng);
	}
}
