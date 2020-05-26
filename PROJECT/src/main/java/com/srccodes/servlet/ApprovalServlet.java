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
 
@WebServlet(urlPatterns = { "/approval"})
/*
 * There's no JSP file for this link, the servlet simply performs some operations using parameters received in the link
 * The operations are: 
 * -owner approving a booking
 * -owner rejecting a booking
 * -owner changing the availability of one of his properties
 * -admin approving / rejecting an ad request from an owner
 */
public class ApprovalServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public ApprovalServlet() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
 
	   String available = request.getParameter("available");
	   
	   if(available != null) {//Owner wants to change the availability of one of his properties
		   int pid = Integer.parseInt(request.getParameter("property"));
		   System.out.println("UPDATING " + pid + " WITH " +  available);
		   try {
			DBUtils.updateAvailability(MyUtils.getStoredConnection(request), pid, Integer.parseInt(available));
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		   response.sendRedirect(request.getContextPath() + "/property?name=" + pid);//Operation successful, go back to the property's page
		   return;
	   }
	   
	   int bookingId = Integer.parseInt(request.getParameter("booking"));
	   if(bookingId == 0) {//Admin has approved an ad
		   
		   Connection conn = MyUtils.getStoredConnection(request);
		   String id = request.getParameter("property");
		   String name = null;
		   try {
			name = DBUtils.getPropertyName(conn, Integer.parseInt(id));
		} catch (NumberFormatException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		   int notificationId = Integer.parseInt(request.getParameter("notification"));
		   try {
			   System.out.println("NAME IS: " + id);
			DBUtils.validateProperty(conn, DBUtils.getPropertyId(conn, name));
			DBUtils.deleteNotification(conn, notificationId);
			String message = "Your ad for property " + name + " has been approved.owner=" + DBUtils.getPropertyOwner(conn, DBUtils.getPropertyId(conn, name));
			request.setAttribute("ownerPropertyMessage", message);//Make the request attribute listener send the notification
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		   response.sendRedirect(request.getContextPath() + "/adminNotifications");//We came from an admin into this servlet, go back
		   
	   }else {//A booking request has been answered
		   
		   int status = Integer.parseInt(request.getParameter("status"));
		   int notificationId = Integer.parseInt(request.getParameter("notification"));
		   Connection conn = MyUtils.getStoredConnection(request);
		   String message = null;
		   String sql = "select check_in, check_out from booking where booking_id = ?";
		   try {
		   PreparedStatement pstm = conn.prepareStatement(sql);
		   pstm.setInt(1, bookingId);
		   ResultSet rs = pstm.executeQuery();
		   java.sql.Date checkIn = null;
		   java.sql.Date checkOut = null;
		   while(rs.next()) {
			   checkIn = rs.getDate(1);
			   checkOut = rs.getDate(2);
		   }
		   
		   message = "Your booking for " + DBUtils.getPropertyFromBooking(conn, bookingId) + " from " + checkIn + " to " + checkOut + " has been ";
		   if(status == 1)
			   message += "confirmed.";
		   else message += "rejected.";
		   message += "client=" + DBUtils.getClientId(conn, DBUtils.getClientFromBooking(conn, bookingId)) + "booking=" + bookingId;
		   }catch(SQLException e) {
			   e.printStackTrace();
		   }
		   try {
			DBUtils.updateBooking(conn, bookingId, status);
			DBUtils.deleteNotification(conn, notificationId);
			request.setAttribute("bookingConfirmed", message);//Trigger the request attribute listener, send a notification to the client
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		   
		   
	       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/notifications.jsp");//Job is done, go back to the notifications page that led here
	        
	       dispatcher.forward(request, response);
	   }
        
   }
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       doGet(request, response);
   }
 
}
