package com.srccodes.servlet;

import java.io.IOException;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@WebServlet(urlPatterns = { "/notifications"})
/*
 * This servlet handles the owner's list of notifications, it has no extra functionality as everything is handled by JS
 */
public class NotificationsServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public NotificationsServlet() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

	   
       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/notifications.jsp");
        
       dispatcher.forward(request, response);
        
   }
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       doGet(request, response);
   }
 
}
