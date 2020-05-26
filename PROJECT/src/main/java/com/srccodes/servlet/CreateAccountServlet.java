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

import com.srccodes.beans.User;
import com.srccodes.utils.DBUtils;
import com.srccodes.utils.MyUtils;
 
@WebServlet(urlPatterns = { "/createAccount"})
/*
 * This servlet brings up the account creation page
 * The post method handles constraints and the creation itself
 */
public class CreateAccountServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public CreateAccountServlet() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
 
        
       // Forward to /WEB-INF/views/homeView.jsp
       // (Users can not access directly into JSP pages placed in WEB-INF)
       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/createAccount.jsp");
        
       dispatcher.forward(request, response);
        
   }
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
	   //Get user input
	   String userName = request.getParameter("userName");
       String password = request.getParameter("password");
       String email = request.getParameter("email");
       String firstName = request.getParameter("firstName");
       String lastName = request.getParameter("lastName");
       String type = request.getParameter("userType");
       
       boolean hasError = false;
       String errorString = "";

       //Check constraints
       if (userName == null || password == null || email == null || userName.length() == 0 || password.length() == 0 || email.length() == 0) {
           hasError = true;
           errorString = "Required username and password and email!";
       }else {
       Connection conn = MyUtils.getStoredConnection(request);
       
       User user = null;
	try {
		user = DBUtils.findUser(conn, userName);
	} catch (SQLException e1) {
		hasError = true;
		errorString += "INTERNAL SQL ERROR!";
		e1.printStackTrace();
	}
	
	//Check if credentials are available
       
       if(user != null) {
    	   hasError = true;
    	   errorString += "Credentials already in use!";
       }else {
       try {
    	   
       DBUtils.addUser(conn, userName, password, email, type, firstName, lastName);
       }catch (SQLException e) {
    	   hasError = true;
    	   errorString = e.getMessage();
    	   e.printStackTrace();
       }
       response.sendRedirect(request.getContextPath() + "/login");//It went well, go the login screen
       }
       }
       
       if(hasError) {
    	   request.setAttribute("errorString", errorString);

           // Forward to /WEB-INF/views/login.jsp
           RequestDispatcher dispatcher //
                   = this.getServletContext().getRequestDispatcher("/WEB-INF/views/createAccount.jsp");//Errors showed up, try again

           dispatcher.forward(request, response);
       }
   }
 
}
