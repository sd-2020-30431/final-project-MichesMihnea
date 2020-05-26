package com.in28minutes.springboot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.in28minutes.springboot.web.service.ControlPanelService;
import com.srccodes.beans.User;

@Controller
@SessionAttributes("user")
public class ControlPanelController {
	
	@Autowired
	ControlPanelService service;

	@RequestMapping(value="/controlPanel", method = RequestMethod.GET)
	public String showControlPanelPage(ModelMap model,  @RequestParam(defaultValue = "") String successString){
		User user = (User) model.get("user");
		model.put("successString", successString);
		if(user == null)
			return "redirect:/login";
		
		String currentApproval = service.getCurrentApproval((User) model.get("user"));
		model.put("currentApproval", currentApproval);;
		return "controlPanel";
	}
	
	@RequestMapping(value="/controlPanel", method = RequestMethod.POST)
	public String changeApprovalMethod(ModelMap model, @RequestParam String approval){
		String currentApproval = approval;
		model.put("currentApproval", approval);
		
		service.updateApproval((User) model.get("user"), approval);
		return "controlPanel";
	}
}
