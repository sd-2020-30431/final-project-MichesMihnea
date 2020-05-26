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
 
@WebServlet(urlPatterns = { "/myAccount" })
/*
 * This is the servlet holding the main account page of the user, probably the most important one in the application
 * The post method performs the log out
 */
public class MyAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public MyAccountServlet() {
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
        Connection conn = MyUtils.getStoredConnection(request);
        request.setAttribute("user", loginedUser);
        if(loginedUser.getType() == "client")
        {
        try {
			request.setAttribute("lastName", DBUtils.getClientLastName(conn, DBUtils.getClientId(conn, loginedUser.getUserName())));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }else if(loginedUser.getType() == "owner"){
        	try {
    			request.setAttribute("lastName", DBUtils.getOwnerLastName(conn, DBUtils.getOwnerId(conn, loginedUser.getUserName())));
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }
 
        // If the user has logged in, then forward to the page
        // /WEB-INF/views/myAccount.jsp

        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/myAccount.jsp");
        dispatcher.forward(request, response);
 
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	//Logging out
        HttpSession session = request.getSession();
        session.removeAttribute("loginedUser");
        MyUtils.deleteUserCookie(response);
        response.sendRedirect(request.getContextPath() + "/login");
    }
 
}