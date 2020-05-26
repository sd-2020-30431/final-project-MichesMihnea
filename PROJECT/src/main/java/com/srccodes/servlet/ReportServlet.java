package com.srccodes.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.srccodes.utils.DBUtils;
import com.srccodes.utils.MyUtils;
 
@WebServlet(urlPatterns = { "/report"})
/*
 * This servlet does not have its own page, it only performs the creation of a new report for the admins to review
 */
public class ReportServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public ReportServlet() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
	   
	   //Get the reported comment and send a message to the admins

	   int reviewId = Integer.parseInt(request.getParameter("review"));
	   int propertyId = Integer.parseInt(request.getParameter("property"));
	   int clientId = Integer.parseInt(request.getParameter("client"));
	   Connection conn = MyUtils.getStoredConnection(request);
	   String ownerName = null;
	try {
		ownerName = DBUtils.getOwnerName(conn, propertyId);
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

	   String message = null;
	try {
		message = "Owner " + ownerName + " has reported a review from client " + DBUtils.getClientFirstName(conn, clientId) + " " + DBUtils.getClientLastName(conn, clientId) + ".owner=" + DBUtils.getPropertyOwner(conn, propertyId) + 
				"client=" + clientId + "id=" + propertyId + "review=" + reviewId;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   request.setAttribute("newClientReport", message);//Trigger the request attribute listener in order to notify the admins
	   response.sendRedirect(request.getContextPath() + "/property?name=" + propertyId);//Go back to the page that led here
        
   }
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       doGet(request, response);
   }
 
}
