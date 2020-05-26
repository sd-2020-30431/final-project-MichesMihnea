package com.srccodes.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
 
@WebServlet(urlPatterns = { "/booking"})
/*
 * This url leads to a page containing the details for a booking
 * It has a button, and the post method can cancel the booking
 */
@MultipartConfig
public class BookingServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public BookingServlet() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
 
        //Use the url parameters, and bring up the web page
       String id = request.getParameter("id");
       request.setAttribute("bookingid", id);
       Connection conn = MyUtils.getStoredConnection(request);
       String name = null;
       try {
		name = DBUtils.getPropertyFromBooking(conn, Integer.parseInt(id));
	} catch (NumberFormatException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
       request.setAttribute("propertyName", name);
       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/booking.jsp");
       dispatcher.forward(request, response);
        
   }
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
	   		//Form has been submitted, we have to cancel the booking
	   		Connection conn = MyUtils.getStoredConnection(request);
	   		HttpSession session = request.getSession();
	   		try {
				DBUtils.deleteBooking(conn, Integer.parseInt( (String) session.getAttribute("bookingid")));
			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Forward to /WEB-INF/views/login.jsp
			RequestDispatcher dispatcher //
			= this.getServletContext().getRequestDispatcher("/WEB-INF/views/viewBookings.jsp");//Go back to the list of bookings as this one is no longer available

			dispatcher.forward(request, response);
   }
 
}
