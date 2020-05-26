package com.srccodes.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.srccodes.beans.User;
import com.srccodes.utils.DBUtils;
import com.srccodes.utils.MyUtils;
 
@WebServlet(urlPatterns = { "/showProperty"})
/*
 * This servlet holds the page corresponding to the client's view of a property ad (different from that of the owner)
 * The post method creates a booking for this property according to the data received on the url
 */
@MultipartConfig
public class ShowPropertyServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public ShowPropertyServlet() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
 
        
       //Get url parameters and set accordingly
	   
       String name = request.getParameter("name");
       String address = request.getParameter("address");
       String checkIn = request.getParameter("checkin");
		String checkOut = request.getParameter("checkout");
		String guests = request.getParameter("guests");
		String price = request.getParameter("price");
		request.setAttribute("totalprice", price);
		request.setAttribute("checkin", request.getParameter("checkin"));
		request.setAttribute("checkout", request.getParameter("checkout"));
		request.setAttribute("guests", request.getParameter("guests"));
       request.setAttribute("propertyName", name);
       request.setAttribute("address", address);
       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/showProperty.jsp");
       dispatcher.forward(request, response);
        
   }
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
	   
	   //A booking request has to be created
	   
	   HttpSession session = request.getSession();
	   User client = MyUtils.getLoginedUser(session);

		if(client == null) {//Session has expired, go to login page
			session.setAttribute("resetPasswordErrorString", "Please log in to access this feature!");
			RequestDispatcher dispatcher //
			= this.getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp");

			dispatcher.forward(request, response);

			return;
		}
		boolean hasError = false;
		
		//Check validity of input one more time, just to be sure
		
	   String pattern = "MM/dd/yyyy";
	   Date date1 = null, date2 = null;
	   SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	   try {
		date1 = simpleDateFormat.parse((String) session.getAttribute("checkin"));
	} catch (ParseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		hasError = true;
	}
	   try {
		date2 = simpleDateFormat.parse((String) session.getAttribute("checkout"));
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		hasError = true;
	}
	   int clientId = 0;
	   Connection conn = MyUtils.getStoredConnection(request);
	   try {
		clientId = DBUtils.getClientId(conn, client.getUserName());
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		hasError = true;
	}
	   	int propertyId = 0;
	   	propertyId = (int) session.getAttribute("propertyId");
	   	if (propertyId == 0 || clientId == 0)
	   		hasError = true;
	   	
	   	if(hasError) {
	   		session.setAttribute("resetPasswordErrorString", "Something went wrong!");
	   		RequestDispatcher dispatcher //
			= this.getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp");

			dispatcher.forward(request, response);
	   	}
	   	
	   	int ownerId = 0;
		try {
			ownerId = DBUtils.getPropertyOwner(conn, propertyId);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	   	
	   	try {
	   		if(DBUtils.getApproval(conn, ownerId) == 1)
	   			DBUtils.addBooking(conn, date1, date2,(float) session.getAttribute("price"), clientId, propertyId, 0);//Booking gets approved manually
	   		else DBUtils.addBooking(conn, date1, date2,(float) session.getAttribute("price"), clientId, propertyId, 1);//Booking gets approved automatically
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   	
	   	int bookingId = 0;
	   	
	   	try {
			bookingId = DBUtils.getBookingId(conn, date1, date2, (float) session.getAttribute("price"), clientId, propertyId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   	
	   	request.setAttribute("newbooking", (String) session.getAttribute("bookingmessage") + "booking=" + bookingId);//Trigger the request attribute listener in order to notify the owner about this new booking
	   	
	   	response.sendRedirect(request.getContextPath() + "/viewBookings");//success, show the client's bookings
	   	
   }
 
}
