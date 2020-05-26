package com.srccodes.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@WebServlet(urlPatterns = { "/index"})
/*
 * This servlet holds the home page, the post method handles the search for properties
 */
public class HomeServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public HomeServlet() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
 
        
       // Forward to /WEB-INF/views/homeView.jsp
       // (Users can not access directly into JSP pages placed in WEB-INF)
	   
       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp");
        
       dispatcher.forward(request, response);
       
        
   }
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
	   
	   //Get input data 
	   String location = request.getParameter("location");
	   String checkIn = request.getParameter("checkin_date");
	   String checkOut = request.getParameter("checkout_date");
	   String guests = request.getParameter("guests");
	   SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	   Date date1 = null, date2 = null;
       try {
		date1 = sdf.parse(checkIn);
	} catch (ParseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace(); 
	}
       try {
		date2 = sdf.parse(checkOut);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       //Perform input constraint checks
       if(date2.compareTo(date1) < 0 || date1.compareTo(new Date(System.currentTimeMillis())) < 0) {
    	   
    	   request.setAttribute("invalidDate", 1);
    	   request.setAttribute("errorString", "Please enter valid dates!");
    	   RequestDispatcher dispatcher //
           = this.getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp");

    	   dispatcher.forward(request, response);//Bad input, try again
       }
       
       if(location == null || location.equals("")) {
    	   		
    	   request.setAttribute("invalidDate", 1);
    	   request.setAttribute("errorString", "Please enter a valid location!");
    	   RequestDispatcher dispatcher //
           = this.getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp");

    	   dispatcher.forward(request, response);//Bad input, try again
       }
       
       if(guests.equals("0")) {
	   		
    	   request.setAttribute("invalidDate", 1);
    	   request.setAttribute("errorString", "Please enter the number of guests!");
    	   RequestDispatcher dispatcher //
           = this.getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp");

    	   dispatcher.forward(request, response);//Bad input, try again
       }
	   
	   //Input is good, perform the search
	   response.sendRedirect(request.getContextPath() + "/search?location=" + location + "&checkin=" + checkIn + "&checkout=" + checkOut + "&guests=" + guests);
   }
 
}
