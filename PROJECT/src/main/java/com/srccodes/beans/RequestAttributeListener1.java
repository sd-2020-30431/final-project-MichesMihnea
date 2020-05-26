package com.srccodes.beans;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;

import com.srccodes.utils.DBUtils;
import com.srccodes.utils.MyUtils;

import java.util.*;
/*
 * User to implement the Observer design pattern
 * This listener monitors changes in the request attributes
 * Some of them are important 
 * We use this class in order to send notifications to users in regards to various events
 * All attributes of interest hold a message with some parameters encoded at the end, the job of
 * this class is to decode these parameters and fulfill the task accordingly
 */
@WebListener
public class RequestAttributeListener1 implements ServletRequestAttributeListener
{

public void attributeAdded(ServletRequestAttributeEvent srae)
{	    
	if(srae.getName() == "newbooking") {//A new booking has been added, notify the owner of the booked property
		Connection conn = MyUtils.getStoredConnection(srae.getServletRequest());
		String message = (String) srae.getValue();
		int index = 0;
		for (int i = -1; (i = message.indexOf("owner", i + 1)) != -1; i++) {
		    index = i;
		}
		
		int j = 0;
		
		for (int i = -1; (i = message.indexOf("booking", i + 1)) != -1; i++) {
		    j = i;
		}
		
		try {
			DBUtils.addNotification(conn, message, Integer.parseInt(message.substring(index + 6, j)), DBUtils.getApproval(conn, Integer.parseInt(message.substring(index + 6, j))));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	else if(srae.getName() == "bookingConfirmed") {//A booking request has been confirmed, notify the client
		Connection conn = MyUtils.getStoredConnection(srae.getServletRequest());
		String message = (String) srae.getValue();
		int index = 0;
		for (int i = -1; (i = message.indexOf("client", i + 1)) != -1; i++) {
		    index = i;
		}
		
		int j = 0;
		
		for (int i = -1; (i = message.indexOf("booking", i + 1)) != -1; i++) {
		    j = i;
		}
		
		try {
			DBUtils.addNotificationClient(conn, message, Integer.parseInt(message.substring(index + 7, j)));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	else if(srae.getName() == "newClientNotification") {//A client has a new notification
		Connection conn = MyUtils.getStoredConnection(srae.getServletRequest());
		String message = (String) srae.getValue();
		int index = 0;
		for (int i = -1; (i = message.indexOf("client", i + 1)) != -1; i++) {
		    index = i;
		}
		
		try {
			DBUtils.addNotificationClient(conn, message, Integer.parseInt(message.substring(index + 7, message.length())));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	else if(srae.getName() == "newPropertyNotification") {//An owner wants to list a new property, notify admins - all admins can see this
		Connection conn = MyUtils.getStoredConnection(srae.getServletRequest());
		String message = (String) srae.getValue();
		int index = 0;
		for (int i = -1; (i = message.indexOf("owner", i + 1)) != -1; i++) {
		    index = i;
		}
		
		int j = 0;
		
		for (int i = -1; (i = message.indexOf("property", i + 1)) != -1; i++) {
		    j = i;
		}
		
		try {
			DBUtils.addNotificationAdmin(conn, message, 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	else if(srae.getName() == "ownerPropertyMessage") {//Owner's property ad request has received a response, notify the owner
		Connection conn = MyUtils.getStoredConnection(srae.getServletRequest());
		String message = (String) srae.getValue();
		int index = 0;
		for (int i = -1; (i = message.indexOf("owner", i + 1)) != -1; i++) {
		    index = i;
		}
		
		try {
			DBUtils.addNotification(conn, message, Integer.parseInt(message.substring(index + 6, message.length())), 0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	else if(srae.getName() == "newPropertyReviewNotification") {//Owner has received a new review for one of his properties, notify the owner
		Connection conn = MyUtils.getStoredConnection(srae.getServletRequest());
		String message = (String) srae.getValue();
		int index = 0;
		for (int i = -1; (i = message.indexOf("owner", i + 1)) != -1; i++) {
		    index = i;
		}
		int j = 0;
		for (int i = -1; (i = message.indexOf("property", i + 1)) != -1; i++) {
			 j = i;
		}
		
		try {
			DBUtils.addNotification(conn, message, Integer.parseInt(message.substring(index + 6, j)), 0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	else if(srae.getName() == "newClientReport") {//A client has been reported, notify the admins
		Connection conn = MyUtils.getStoredConnection(srae.getServletRequest());
		String message = (String) srae.getValue();
		int index = 0;
		for (int i = -1; (i = message.indexOf("owner", i + 1)) != -1; i++) {
		    index = i;
		}
		int j = 0;
		for (int i = -1; (i = message.indexOf("client", i + 1)) != -1; i++) {
			 j = i;
		}
		
		try {
			DBUtils.addNotificationAdmin(conn, message, 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
}

@Override
public void attributeRemoved(ServletRequestAttributeEvent srae) {
	// TODO Auto-generated method stub
	
}

@Override
public void attributeReplaced(ServletRequestAttributeEvent srae) {
	// TODO Auto-generated method stub
	
}
}