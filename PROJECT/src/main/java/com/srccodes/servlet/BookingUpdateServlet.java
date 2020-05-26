package com.srccodes.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.srccodes.utils.DBUtils;
import com.srccodes.utils.MyUtils;
 
@WebServlet(urlPatterns = { "/bookingUpdate"})
/*
 * This servlet brings up no web page, but it performs some operations, such as:
 * -owner performs a check in for a booking
 * -owner performs a check out for a booking
 */
public class BookingUpdateServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public BookingUpdateServlet() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
	   

	   int bookingId = Integer.parseInt(request.getParameter("id"));
	   Connection conn = MyUtils.getStoredConnection(request);
	   String action = request.getParameter("action");
	   String name = request.getParameter("name");
	   System.out.println("NAME: " + name);
	   //perform the specified action
	   if(action.equals("checkin")) {

	   
		   try {
			   DBUtils.checkIn(conn, bookingId);
		   } catch (SQLException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   }
	   }
	   
	   if(action.equals("checkout")) {
		   
		   try {
			   DBUtils.checkOut(conn, bookingId);
		   } catch (SQLException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   }
	   }
	   
		response.sendRedirect(request.getContextPath() + "/propertyBookings?name=" + name);//go back to the page that led here, as there's no JSP for this servlet
        
   }
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       doGet(request, response);
   }
 
}
