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
 
@WebServlet(urlPatterns = { "/resetPassword"})
/*
 * This servlet handles the page which changes a user's password
 */
public class ResetPasswordServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public ResetPasswordServlet() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
 
       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/resetPassword.jsp");
        
       dispatcher.forward(request, response);
        
   }
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
	   
	   //Get user's input
	   
       String newPassword = request.getParameter("newPassword");
       String confirmPassword = request.getParameter("confirmPassword");
       boolean valid = newPassword.equals(confirmPassword);
       
       HttpSession session = request.getSession();

       User user = null;
       boolean hasError = false;
       String errorString = null;
       
       //Perform constraint checks

       if (newPassword == null || confirmPassword == null || newPassword.length() == 0 || confirmPassword.length() == 0) {
           hasError = true;
           errorString = "Please enter the new password!";
       } else if(!valid){
    	   hasError = true;
    	   errorString = "Passwords do not match!";
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

               if (user == null) {//User not found, big error, abort
                   hasError = true;
                   errorString = "INTERNAL SERVER ERROR!";
               }
               
               DBUtils.changePassword(conn, loginedUser.getUserName(), newPassword);//Perform the actual update
               user.setPassword(newPassword);
               MyUtils.storeLoginedUser(session, user);//Also update the session in order to avoid weird surprises
               }
           } catch (SQLException e) {
               e.printStackTrace();
               hasError = true;
               errorString = e.getMessage();
           }
       }

       if (hasError) {

           // Store information in request attribute, before forward.
           request.setAttribute("errorString", errorString);

           // Forward to /WEB-INF/views/resetPassword.jsp
           RequestDispatcher dispatcher //
                   = this.getServletContext().getRequestDispatcher("/WEB-INF/views/resetPassword.jsp");

           dispatcher.forward(request, response);
       }
       
       else response.sendRedirect(request.getContextPath() + "/myAccount");
   }

}
 
