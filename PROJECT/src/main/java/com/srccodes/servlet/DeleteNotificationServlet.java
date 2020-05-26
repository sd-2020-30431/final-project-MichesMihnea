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
 
@WebServlet(urlPatterns = { "/deleteNotification"})
/*
 * This url is used in order to delete notifications
 * This deletion does come bundled with other operations in certain cases
 */
public class DeleteNotificationServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public DeleteNotificationServlet() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
	   

	   int notificationId = Integer.parseInt(request.getParameter("notification"));
	   String owner = request.getParameter("owner");
	   Connection conn = MyUtils.getStoredConnection(request);
	   //First of all, delete the notification
	   try {
		DBUtils.deleteNotification(conn, notificationId);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
	   String pid = request.getParameter("property");
	   System.out.println("PROPERTY: " + pid);
	   String name = null;
	   if(pid != null) {
	try {
		name = DBUtils.getPropertyName(conn, Integer.parseInt(pid));
	} catch (NumberFormatException | SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	   }
	   if(name != null) {//This was a negative response to an ad request. Therefore, the property ad is rejected and should be deleted
		   try {
			int id = DBUtils.getPropertyId(conn, name);
			String message = "Your ad for property " + name + " has been rejected.owner=" + DBUtils.getPropertyOwner(conn, DBUtils.getPropertyId(conn, name));
			DBUtils.deleteProperty(conn, id);
			//Trigger the request attribute listener in order to notify the owner
			request.setAttribute("ownerPropertyMessage", message);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/adminNotifications.jsp");//We came from an admin's notifications, go back
	        
	       dispatcher.forward(request, response);
		   return;
	   }
	   
	   if(owner != null)
	   {
		   RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/notifications.jsp");//Deleted a simple message, so we came from an owner, go back there
	        
	       dispatcher.forward(request, response);
		   
	   }else {//An admin deletes a user review

	   String review = request.getParameter("review");
	   
	   if(review != null) {
		   try {
			   String ban = request.getParameter("ban");
			   
			   if(ban != null) {
				   String sql = "select client_id from review_property where review_property_id = ?";
				   PreparedStatement pstm = conn.prepareStatement(sql);
				   pstm.setInt(1, Integer.parseInt(review));
				   ResultSet rs = pstm.executeQuery();
				   int clientId = 0;
				   
				   while(rs.next()) {
					   clientId = rs.getInt(1);
				   }
				   
				   DBUtils.banUser(conn, DBUtils.getUserFromClient(conn, clientId), 1);
			   }
			DBUtils.deleteReviewProperty(conn, Integer.parseInt(review));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		   RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/adminNotifications.jsp");//We came from an admin's notifications, go back there
	        
	       dispatcher.forward(request, response);
	   }
	   
	   else {
		   
       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/clientNotifications.jsp");//We came from a client, go back there
       dispatcher.forward(request, response);
       
	   }
	   }
        
   }
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       doGet(request, response);
   }
 
}
