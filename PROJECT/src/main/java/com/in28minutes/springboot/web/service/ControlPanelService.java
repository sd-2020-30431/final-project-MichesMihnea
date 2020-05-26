package com.in28minutes.springboot.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in28minutes.springboot.web.DBUtils;
import com.srccodes.beans.User;

@Service
public class ControlPanelService {

	@Autowired
	DBUtils utils;
	
	public String getCurrentApproval(User user) {
		return utils.getApproval(user);
	}
	
	public void updateApproval(User user, String approval) {
		utils.updateApproval(user, approval);
	}
}
