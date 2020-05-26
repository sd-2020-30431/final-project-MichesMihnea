package com.in28minutes.springboot.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.in28minutes.springboot.web.service.ViewBookingsService;
import com.in28minutes.springboot.web.service.ViewNotificationsService;
import com.srccodes.beans.Booking;
import com.srccodes.beans.Notification;
import com.srccodes.beans.User;

@Controller
@SessionAttributes("user")
public class ViewNotificationsController {

	@Autowired
	ViewNotificationsService service;
	
	@RequestMapping(value="/myNotifications", method = RequestMethod.GET)
	public String showMyAccountPage(ModelMap model, @RequestParam(defaultValue = "") String successString){
		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		List <Notification> notifications = service.getNotifications(user);
		model.put("notifications", notifications);
		model.put("successString", successString);	
		
		return "myNotifications";
	}
	
	@RequestMapping(value="/deleteNotification", method = RequestMethod.GET)
	public String deleteNotification(ModelMap model, @RequestParam Long notificationId, @RequestParam(defaultValue = "0") Integer denial,
			RedirectAttributes redirectAttributes){
		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		service.deleteNotification(notificationId, denial);
		redirectAttributes.addAttribute("successString", "Notification deleted successfully!");
			
		return "redirect:/myNotifications";
	}
	
	@RequestMapping(value="/approveNotification", method = RequestMethod.GET)
	public String aproveNotification(ModelMap model, @RequestParam Long notificationId, RedirectAttributes redirectAttributes){
		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		service.approveBooking(notificationId);
		service.deleteNotification(notificationId, 0);
		redirectAttributes.addAttribute("successString", "Notification deleted successfully!");
			
		return "redirect:/myNotifications";
	}
	
	@RequestMapping(value="/deleteRequest", method = RequestMethod.GET)
	public String deleteRequest(ModelMap model, @RequestParam Long notificationId, @RequestParam(defaultValue = "0") Integer denial,
			RedirectAttributes redirectAttributes){
		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		service.deleteRequest(notificationId, denial);
		redirectAttributes.addAttribute("successString", "Request deleted successfully!");
			
		return "redirect:/myNotifications";
	}
	
	@RequestMapping(value="/approveRequest", method = RequestMethod.GET)
	public String aproveRequest(ModelMap model, @RequestParam Long notificationId, RedirectAttributes redirectAttributes){
		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		service.approveRequest(notificationId);
		service.deleteNotification(notificationId, 0);
		redirectAttributes.addAttribute("successString", "Request approved successfully!");
			
		return "redirect:/myNotifications";
	}
	
	@RequestMapping(value="/report", method = RequestMethod.GET)
	public String reportReview(ModelMap model, @RequestParam Long reviewId, RedirectAttributes redirectAttributes){
		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		service.sendReviewReportNotification(user, reviewId);
		redirectAttributes.addAttribute("successString", "Review reported successfully!");
			
		return "redirect:/myAccount";
	}
	
}
