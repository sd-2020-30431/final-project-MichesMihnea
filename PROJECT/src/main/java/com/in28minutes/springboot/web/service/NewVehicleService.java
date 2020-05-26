package com.in28minutes.springboot.web.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.in28minutes.springboot.web.DBUtils;
import com.srccodes.beans.Booking;
import com.srccodes.beans.Car;
import com.srccodes.beans.Guest;
import com.srccodes.beans.Owner;
import com.srccodes.beans.Photo;
import com.srccodes.beans.User;
import com.srccodes.beans.Vehicle;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


@Service
public class NewVehicleService {
	
	@Autowired
	DBUtils utils;
	
	@Autowired
    private ApplicationEventPublisher applicationEventPublisher;
	
	public void setMakes(ModelMap model) {
		String makes[] = {"BMW", "Mercedes-Benz", "Audi", "Porsche"};
		model.put("makesList", makes);
	}
	
	public void setProductionYears(ModelMap model) {
		String makes[] = new String[10];
		
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		
		for(int i = 0; i < 10; i ++)
			makes[i] = Integer.toString(currentYear - i);
		
		model.put("yearsList", makes);
	}
	
	public void setFuelTypes(ModelMap model) {
		String fuels[] = {"Gas", "Diesel", "Electric", "LPG"};
		model.put("fuelsList", fuels);
	}
	
	public boolean validateInput(ModelMap model, String makes, String models, String years, String fuels, String power,
			String price, String emissions, String seats, Float lat, Float lng) {
		
		boolean hasError = false;
		String errorString = "Invalid input! Please check: ";
		
		if(makes.equals("make") || makes.equals("-")) {
			hasError = true;
			errorString += "make";
		}
		
		if(models.length() <= 0 || models.length() >= 32) {
			if(hasError) {
				errorString += ", ";
			}
			
			hasError = true;
			errorString += "model";
		}
		
		if(years.equals("year") || years.equals("-")) {
			if(hasError) {
				errorString += ", ";
			}
			
			hasError = true;
			errorString += "production year";
		}
		
		if(fuels.equals("fuel") || fuels.equals("-")) {
			if(hasError) {
				errorString += ", ";
			}
			
			hasError = true;
			errorString += "fuel type";
		}
		
		try {
			if(Integer.parseInt(power) <= 0 || Integer.parseInt(power) > 10000) {
				if(hasError) {
					errorString += ", ";
				}
				
				hasError = true;
				errorString += "power";
			}
		}catch(NumberFormatException nfex) {
			if(hasError) {
				errorString += ", ";
			}
			
			hasError = true;
			errorString += "power";
		}
		
		try {
			if(Float.parseFloat(price) <= 0 || Float.parseFloat(price) > 10000) {
				if(hasError) {
					errorString += ", ";
				}
				
				hasError = true;
				errorString += "price";
			}
		}catch (NumberFormatException nfex) {
			if(hasError) {
				errorString += ", ";
			}
			
			hasError = true;
			errorString += "price";
		}
		
		if(emissions.equals("emissions") || emissions.equals("-")) {
			if(hasError) {
				errorString += ", ";
			}
			
			hasError = true;
			errorString += "emissions";
		}
		
		if(seats.equals("seats") || seats.equals("-")) {
			if(hasError) {
				errorString += ", ";
			}
			
			hasError = true;
			errorString += "seats";
		}
		
		System.out.println("COMPARING");
		System.out.println(lat + " " + lng);
		System.out.println("lat == 0 " + (lat == 0));
		
		if(lat == 0 || lng == 0) {
			if(hasError) {
				errorString += ", ";
			}
			
			hasError = true;
			errorString += "position";
		}
		
		if(hasError)
			model.put("errorString", errorString);
		
		return hasError;
	}
	
	public void uploadPhotos(HttpServletRequest request, Vehicle vehicle) throws IOException, ServletException {
		System.out.println("TRYING TO UPLOAD...");
		List <String> imgList = new ArrayList <String> ();
		List<Part> fileParts = request.getParts().stream().filter(part -> 
		"file".equals(part.getName())).collect(Collectors.toList()); // Retrieves <input type="file" name="file" multiple="true">
		java.io.File uploads = new java.io.File("D:\\Users\\Miches\\Desktop\\PROJECT\\src\\main\\webapp\\uploads");
		if(fileParts.size() > 0) {
			for (Part filePart : fileParts) {//Now iterate over the photos and upload them, then link them to the property in the database
				System.out.println("IN FOREACH...");
				//The server has to be refreshed in order to display the photos!
				String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
				InputStream fileContent = filePart.getInputStream();
				File file = File.createTempFile("somefilename-", ".png", uploads);
				imgList.add(file.getName());
	
				try (InputStream input = filePart.getInputStream()) {
					Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);//Photos get copied to our server
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		Iterator <String> it = imgList.iterator();
		while(it.hasNext()) {
			Photo photo = new Photo();
			photo.setPath(it.next());
			utils.addPhoto(photo, vehicle);
		}
	}
	
	public Car addCar(User user, String makes, String models, String years, String fuels, String power,
			String price, String emissions, String seats, float lat, float lng) {
		
		Car car = new Car();
		car.setMake(makes);
		car.setModel(models);
		car.setYear(years);
		car.setFuel(fuels);
		car.setPower(Integer.parseInt(power));
		car.setPrice(Float.parseFloat(price));
		car.setEmissions(emissions);
		car.setSeats(Integer.parseInt(seats));
		car.setLat(lat);
		car.setLng(lng);
		
		Owner owner = utils.getOwner(user);
		return utils.addCar(owner, car);
		
		
	}
	
	public void sendNewVehicleNotification(User user, Owner owner, Vehicle vehicle) {	

		NotificationEvent notificationEvent = new NotificationEvent(this, "Owner " + owner.getUser().getFirstName() + " " +
				owner.getUser().getLastName() + " has requested to create a new ad for a vehicle.",
				utils.getAdmin(), Optional.empty(), Optional.of(owner), Optional.of(vehicle),
				Optional.empty(), Optional.empty());
		applicationEventPublisher.publishEvent(notificationEvent);
		
	}
	
	public Owner getOwner(User user) {
		return utils.getOwner(user);
	}

}
