package com.in28minutes.springboot.web.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class IndexService {
	
	public boolean validateInput(ModelMap model, String range, String checkin_date, String checkout_date, Integer seats, Float geoLocationLat, Float geoLocationLng) {
		String errorString = "Input error! Please check: ";
		boolean hasError = false;
		boolean badDates = false;
		
		Date startDate = null;
		Date endDate = null;
		try {
			endDate = new SimpleDateFormat("MM/dd/yyyy").parse(checkin_date);
			startDate = new SimpleDateFormat("MM/dd/yyyy").parse(checkout_date); 
			
			if(endDate.compareTo(startDate) > 0 && !badDates) {
				badDates = true;
				if(hasError)
					errorString += ", ";
				errorString += "rental dates";
				hasError = true;
			}
			
			if(endDate.compareTo(new Date(System.currentTimeMillis())) < 0 && !badDates) {
				badDates = true;
				if(hasError)
					errorString += ", ";
				errorString += "rental dates";
				hasError = true;
			}
			
			if(startDate.compareTo(new Date(System.currentTimeMillis())) < 0 && !badDates) {
				badDates = true;
				if(hasError)
					errorString += ", ";
				errorString += "rental dates";
				hasError = true;
			}
			
		} catch (ParseException e) {
			if(hasError)
				errorString += ", ";
			errorString += "rental dates";
			hasError = true;
		} 
		
		if(geoLocationLat == 0 || geoLocationLng == 0) {
			if(hasError)
				errorString += ", ";
			errorString += "rental dates";
			hasError = true;
		}
		
		if(seats == 0) {
			if(hasError)
				errorString += ", ";
			errorString += "number of seats";
			hasError = true;
		}
		
		float newRange = 0;
		
		try {
			newRange = Float.parseFloat(range);
		}catch(NumberFormatException nfex) {
			
		}
		
		if(newRange <= 0) {
			if(hasError)
				errorString += ", ";
			errorString += "range";
			hasError = true;
		}
		
		if(hasError) {
			model.put("errorString", errorString);
		}
		
		return hasError;
	}
}
