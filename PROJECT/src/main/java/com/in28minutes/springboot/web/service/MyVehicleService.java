package com.in28minutes.springboot.web.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in28minutes.springboot.web.DBUtils;
import com.srccodes.beans.Photo;
import com.srccodes.beans.Review;
import com.srccodes.beans.Vehicle;

@Service
public class MyVehicleService {
	
	@Autowired
	DBUtils utils;
	
	public List<Photo> getPhotos(Vehicle vehicle){
		return utils.getPhotos(vehicle);
	}
	
	public Vehicle getVehicle(Long vehicleId) {
		return utils.getVehicle(vehicleId);
	}
	
	public void uploadPhotos(HttpServletRequest request, Vehicle vehicle) throws IOException, ServletException {
		System.out.println("UPLOADING!");
		List <String> imgList = new ArrayList <String> ();
		List<Part> fileParts = request.getParts().stream().filter(part -> 
		"file".equals(part.getName())).collect(Collectors.toList()); // Retrieves <input type="file" name="file" multiple="true">
		java.io.File uploads = new java.io.File("D:\\Users\\Miches\\Desktop\\PROJECT\\src\\main\\webapp\\uploads");
		if(fileParts.size() > 0) {
			for (Part filePart : fileParts) {//Now iterate over the photos and upload them, then link them to the property in the database
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
		}else System.out.println("NO FILES!");
		
		Iterator <String> it = imgList.iterator();
		while(it.hasNext()) {
			Photo photo = new Photo();
			photo.setPath(it.next());
			utils.addPhoto(photo, vehicle);
		}
	}
	
	public void updateAvailability(Vehicle vehicle, Integer available) {
		utils.updateAvailability(vehicle, available);
	}
	
	public Float getVehicleAverage(Vehicle vehicle) {
		return utils.getVehicleAverage(vehicle);
	}
	
	public List <Review> getReviews(Vehicle vehicle){
		return utils.getReviews(vehicle);
	}

}
