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
 
@WebServlet(urlPatterns = { "/controlPanel"})
/*
 * This page brings up a user's control panel, it is common for all users
 * However the post method only applies to owners, that exclusion is handled by JS
 */
public class ControlPanelServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public ControlPanelServlet() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       HttpSession session = request.getSession();

       // Check User has logged on
       User loginedUser = MyUtils.getLoginedUser(session);

       // Not logged in
       if (loginedUser == null) {
           // Redirect to login page.
           response.sendRedirect(request.getContextPath() + "/login");
           return;
       }
       // Store info to the request attribute before forwarding.
       request.setAttribute("user", loginedUser);

       // If the user has logged in, then forward to the page
       // /WEB-INF/views/userInfoView.jsp

       RequestDispatcher dispatcher //
               = this.getServletContext().getRequestDispatcher("/WEB-INF/views/controlPanel.jsp");
       dispatcher.forward(request, response);

   }

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
	   	HttpSession session = request.getSession();
		User user = MyUtils.getLoginedUser(session);
		if(user == null) {//Session has expired, go back to login page
			session.setAttribute("resetPasswordErrorString", "Something went wrong!");
			RequestDispatcher dispatcher //
			= this.getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp");

			dispatcher.forward(request, response);

			return;
		}
		Connection conn = MyUtils.getStoredConnection(request);
		if(user.getType() == "owner") {//Change the owner's approval mode
			int approval = Integer.parseInt(request.getParameter("guests"));
			try {
				DBUtils.updateOwner(conn, DBUtils.getOwnerId(conn, user.getUserName()), approval);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		response.sendRedirect(request.getContextPath() + "/myAccount" ); //Back to the account page
		
   }
 
}
