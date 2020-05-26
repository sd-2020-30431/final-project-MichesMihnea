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
 
@WebServlet(urlPatterns = { "/login"})
/*
 * Here is the servlet that handles the login page
 */
public class LoginServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public LoginServlet() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
 
        
       // Forward to /WEB-INF/views/homeView.jsp
       // (Users can not access directly into JSP pages placed in WEB-INF)
       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp");
        
       dispatcher.forward(request, response);
        
   }
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       String userName = request.getParameter("userName");
       String password = request.getParameter("password");
       
       String rememberMeStr = request.getParameter("rememberMe");
       boolean remember = "Y".equals(rememberMeStr);
       
       //Get user input
       
       HttpSession session = request.getSession();
       
       session.removeAttribute("resetPasswordErrorString");


       User user = null;
       boolean hasError = false;
       String errorString = null;

       if (userName == null || password == null || userName.length() == 0 || password.length() == 0) {//Perform input constraints check
           hasError = true;
           errorString = "Required username and password!";
       } else {
           Connection conn = MyUtils.getStoredConnection(request);
           try {
               // Find the user in the DB.
               user = DBUtils.findUser(conn, userName, password);

               if (user == null) {//Account not found
                   hasError = true;
                   errorString = "User Name or password invalid";
               }
               
               if(DBUtils.getBan(conn, user.getUserName(), user.getPassword()) == 1) {
            	   hasError = true;//Account found but it is banned and not accessible
            	   errorString = "This account is banned";
               }
           } catch (SQLException e) {
               e.printStackTrace();
               hasError = true;
               errorString = e.getMessage();
           }
       }
       // If error, forward to /WEB-INF/views/login.jsp
       if (hasError) {//Something failed, try again and show a message
           user = new User();
           user.setUserName(userName);
           user.setPassword(password);

           // Store information in request attribute, before forward.
           request.setAttribute("errorString", errorString);
           request.setAttribute("user", user);

           // Forward to /WEB-INF/views/login.jsp
           RequestDispatcher dispatcher //
                   = this.getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp");

           dispatcher.forward(request, response);
       }
       // If no error
       // Store user information in Session 
       // And redirect to userInfo page.
       else {//Login successful
           session = request.getSession();
           MyUtils.storeLoginedUser(session, user);

           // If user checked "Remember me".
           if (remember) {
               MyUtils.storeUserCookie(response, user);
           }
           // Else delete cookie.
           else {
               MyUtils.deleteUserCookie(response);
           }

           // Redirect to user's account page
           response.sendRedirect(request.getContextPath() + "/myAccount");
       }
   }

}
 
