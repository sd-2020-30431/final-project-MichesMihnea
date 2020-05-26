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
import javax.servlet.http.HttpSession;

import com.srccodes.beans.User;
import com.srccodes.utils.DBUtils;
import com.srccodes.utils.MyUtils;
 
@WebServlet(urlPatterns = { "/resetEmail"})
/*
 * This servlet handles the page which changes a user's email address
 */
public class ResetEmailServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public ResetEmailServlet() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
 
       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/resetEmail.jsp");
        
       dispatcher.forward(request, response);
        
   }
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
	   
	   //Get user input
	   
       String newEmail = request.getParameter("newPassword");
       String confirmEmail = request.getParameter("confirmPassword");
       boolean valid = newEmail.equals(confirmEmail);
       
       HttpSession session = request.getSession();

       User user = null;
       boolean hasError = false;
       String errorString = null;
       
       //Perform constraint checks

       if (newEmail == null || confirmEmail == null || newEmail.length() == 0 || confirmEmail.length() == 0) {
           hasError = true;
           errorString = "Please enter the new Email!";
       } else if(!valid){
    	   hasError = true;
    	   errorString = "Emails do not match!";
       }else {
           Connection conn = MyUtils.getStoredConnection(request);
           try {
               // Find the user in the DB.
        	   User loginedUser = MyUtils.getLoginedUser(session);
        	   
               // Not logged in
               if (loginedUser == null) {
                   // Redirect to login page.
                   hasError = true;
                   errorString = "INTERNAL SERVER ERROR!";
                   session.setAttribute("resetPasswordErrorString", "Something went wrong!");
                   RequestDispatcher dispatcher //
                   = this.getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp");
                   
                   dispatcher.forward(request, response);
                   
                   return;
                   
               }else {
               user = DBUtils.findUser(conn, loginedUser.getUserName());

               if (user == null) {//Big error, user not found, abort
                   hasError = true;
                   errorString = "INTERNAL SERVER ERROR!";
               }
               
               DBUtils.changeEmail(conn, loginedUser.getUserName(), newEmail);//Perform the update
               user.setEmail(newEmail);
               MyUtils.storeLoginedUser(session, user);//Also update the session, to avoid weird surprises
               }
           } catch (SQLException e) {
               e.printStackTrace();
               hasError = true;
               errorString = e.getMessage();
           }
       }
       // If error, forward to /WEB-INF/views/resetEmail.jsp
       if (hasError) {

           // Store information in request attribute, before forward.
           request.setAttribute("errorString", errorString);

           // Forward to /WEB-INF/views/resetEmail.jsp
           RequestDispatcher dispatcher //
                   = this.getServletContext().getRequestDispatcher("/WEB-INF/views/resetEmail.jsp");

           dispatcher.forward(request, response);
       }
       
       else response.sendRedirect(request.getContextPath() + "/myAccount");
   }

}
 
