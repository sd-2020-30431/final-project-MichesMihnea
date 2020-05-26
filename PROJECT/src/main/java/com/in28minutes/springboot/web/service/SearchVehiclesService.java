package com.in28minutes.springboot.web.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in28minutes.springboot.web.DBUtils;
import com.srccodes.beans.Photo;
import com.srccodes.beans.Vehicle;

@Service
public class SearchVehiclesService {
	
	@Autowired
	DBUtils utils;
	
	public List <Vehicle> getVehiclesInRange(Integer range, Float lat, Float lng){
		
		List <Vehicle> allVehicles = utils.getAllVehicles();
		List <Vehicle> rangeVehicles = new ArrayList <Vehicle> ();
		Iterator <Vehicle> it = allVehicles.iterator();
		
		while(it.hasNext()) {
			Vehicle currentVehicle = it.next();
			Double distance = Math.sqrt((currentVehicle.getLat() - lat) * (currentVehicle.getLat() - lat) + 
					(currentVehicle.getLng() - lng) * (currentVehicle.getLng() - lng));
			
			System.out.println("DISTANCE : " + distance);
			
			if(distance <= range) {
				rangeVehicles.add(currentVehicle);
			}
		}
		
		return rangeVehicles;
	}
	
	public List <Photo> getPhotoList(List <Vehicle> vehicles){
		
		Iterator <Vehicle> vit = vehicles.iterator();
		List <Photo> photos = new ArrayList <Photo> ();
		
		while(vit.hasNext()) {
			Vehicle currentVehicle = vit.next();
			
			if(utils.getPhotos(currentVehicle).size() > 0)
				photos.add(utils.getPhotos(currentVehicle).get(0));
		}
		
		return photos;
	}

}
