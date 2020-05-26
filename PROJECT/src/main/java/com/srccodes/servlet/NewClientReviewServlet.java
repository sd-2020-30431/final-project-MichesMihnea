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

@WebServlet(urlPatterns = { "/newClientReview"})
/*
 * This servlet handles the page that creates a new review for a client (from an owner)
 */
@MultipartConfig

public class NewClientReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public NewClientReviewServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		// Forward to /WEB-INF/views/homeView.jsp
		// (Users can not access directly into JSP pages placed in WEB-INF)
		
		String name = request.getParameter("name");
		request.setAttribute("clientName", name);
		String property = request.getParameter("property");
		request.setAttribute("property", property);
		String booking = request.getParameter("booking");
		request.setAttribute("booking", booking);
		
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/newClientReview.jsp");

		dispatcher.forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//Get owner's input
		
		int score = Integer.parseInt(request.getParameter("score"));
		String comment = request.getParameter("comment");
		HttpSession session = request.getSession();
		User owner = MyUtils.getLoginedUser(session);
		String name = (String) session.getAttribute("clientName");
		String propertyName = (String) session.getAttribute("propertyName");
		int bookingId = (int) session.getAttribute("booking");

		if(owner == null) {//Session expired, go back to login screen
			session.setAttribute("resetPasswordErrorString", "Something went wrong!");
			RequestDispatcher dispatcher //
			= this.getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp");

			dispatcher.forward(request, response);

			return;
		}

		boolean hasError = false;
		String errorString = "";

			Connection conn = MyUtils.getStoredConnection(request);

			User user = null;
			try { //Current user does not exist, big error, abort
				user = DBUtils.findUser(conn, owner.getUserName());
			} catch (SQLException e1) {
				hasError = true;
				errorString += "INTERNAL SQL ERROR!";
				e1.printStackTrace();
			}

			if(user == null) {
				hasError = true;
				errorString += "INTERNAL ERROR!";

			}else {
				
				int ownerId = 0;
				
				try {
					ownerId = DBUtils.getOwnerId(conn, owner.getUserName());
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				try {
					DBUtils.addClientReview(conn, DBUtils.getClientId(conn, name), ownerId, comment, score);//Add the review
					DBUtils.reviewOwner(conn, bookingId);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				response.sendRedirect(request.getContextPath() + "/propertyBookings?name=" + propertyName);//We came from a property's list of bookings, go back there
			}

		if(hasError) {
			request.setAttribute("errorString", errorString);

			// Forward to /WEB-INF/views/login.jsp
			RequestDispatcher dispatcher //
			= this.getServletContext().getRequestDispatcher("/WEB-INF/views/viewBookings.jsp");//Error, go back to the list of bookings

			dispatcher.forward(request, response);
		}
	}

}