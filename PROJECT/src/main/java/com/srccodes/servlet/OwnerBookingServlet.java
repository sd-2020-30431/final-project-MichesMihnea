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
 
@WebServlet(urlPatterns = { "/ownerBooking"})
/*
 * This servlet handles the page holding the owner's view of a booking (it is different from the client's)
 * The post method handles the cancellation of the booking
 */
@MultipartConfig
public class OwnerBookingServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public OwnerBookingServlet() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
 
        
       //Get url parameters and set accordingly
       String id = request.getParameter("id");
       request.setAttribute("bookingid", id);
       Connection conn = MyUtils.getStoredConnection(request);
       String name = null;
       String cname = null;
       try {
		name = DBUtils.getPropertyFromBooking(conn, Integer.parseInt(id));
		cname = DBUtils.getClientFromBooking(conn, Integer.parseInt(id));
	} catch (NumberFormatException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
       System.out.println("NAME IS: " + cname);
       request.setAttribute("propertyName", name);
       request.setAttribute("clientName", cname);
       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/ownerBooking.jsp");
       dispatcher.forward(request, response);
        
   }
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
	   	//Owner wants to cancel the booking
	   
	   		Connection conn = MyUtils.getStoredConnection(request);
	   		HttpSession session = request.getSession();
	   		try {
				DBUtils.deleteBooking(conn, Integer.parseInt( (String) session.getAttribute("bookingid")));
			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	   		request.setAttribute("newClientNotification", session.getAttribute("message"));//Trigger the request attribute listener in order to notify the client about the cancellation

			// Forward to /WEB-INF/views/login.jsp
	   		response.sendRedirect(request.getContextPath() + "/propertyBookings?name=" + (String)session.getAttribute("propertyName"));
   }
 
}
