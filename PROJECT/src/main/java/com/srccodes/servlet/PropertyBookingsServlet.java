package com.srccodes.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.srccodes.utils.DBUtils;
import com.srccodes.utils.MyUtils;
 
@WebServlet(urlPatterns = { "/propertyBookings"})
/*
 * This servlet handles the page showing a list of bookings for a property, everything is handled by JS
 */
public class PropertyBookingsServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public PropertyBookingsServlet() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
	   //Get url parameters and set accordingly
	   
	   String name = (String) request.getParameter("name");
	   request.setAttribute("propertyId", name);
	   try {
		request.setAttribute("name", DBUtils.getPropertyName(MyUtils.getStoredConnection(request), Integer.parseInt(name)));
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/propertyBookings.jsp");
        
       dispatcher.forward(request, response);
        
   }
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       doGet(request, response);
   }
 
}
