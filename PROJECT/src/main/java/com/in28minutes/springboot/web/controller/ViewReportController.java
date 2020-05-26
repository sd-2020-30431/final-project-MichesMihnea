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

import com.in28minutes.springboot.web.service.ViewNotificationsService;
import com.in28minutes.springboot.web.service.ViewReportService;
import com.srccodes.beans.Notification;
import com.srccodes.beans.Review;
import com.srccodes.beans.User;

@Controller
@SessionAttributes("user")
public class ViewReportController {
	
	@Autowired
	ViewReportService service;

	@RequestMapping(value="/viewReport", method = RequestMethod.GET)
	public String showMyAccountPage(ModelMap model, @RequestParam Long reviewId, @RequestParam Long notificationId){
		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		Review review = service.getReview(reviewId);
		model.put("review", review);
		model.put("notificationId", notificationId);
		
		return "viewReport";
	}
	
	@RequestMapping(value="/deleteReport", method = RequestMethod.POST)
	public String deleteReview(ModelMap model, @RequestParam Long reviewId, @RequestParam Long notificationId,
			RedirectAttributes redirectAttributes){
		User user = (User) model.get("user");
		
		if(user == null)
			return "redirect:/login";
		
		service.deleteNotification(notificationId);
		service.deleteReview(reviewId);
		redirectAttributes.addAttribute("successString", "Review deleted successfully!");
		
		return "redirect:/myNotifications";
	}
}
