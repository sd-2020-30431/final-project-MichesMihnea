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

@WebServlet(urlPatterns = { "/newPropertyReview"})
/*
 * This servlet handles the page that creates a new review for a property (from a client)
 */
@MultipartConfig

public class NewPropertyReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public NewPropertyReviewServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		// Forward to /WEB-INF/views/homeView.jsp
		// (Users can not access directly into JSP pages placed in WEB-INF)
		
		String name = request.getParameter("name");
		try {
			request.setAttribute("propertyName", DBUtils.getPropertyName(MyUtils.getStoredConnection(request), Integer.parseInt(name)));
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String booking = request.getParameter("booking");
		request.setAttribute("booking", booking);
		
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/newPropertyReview.jsp");

		dispatcher.forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Get user's input
		
		int score = Integer.parseInt(request.getParameter("score"));
		String comment = request.getParameter("comment");
		HttpSession session = request.getSession();
		User client = MyUtils.getLoginedUser(session);
		String name = (String) session.getAttribute("propertyName");
		int bookingId = Integer.parseInt((String) session.getAttribute("booking"));

		if(client == null) {//Session has expired, go back to login screen
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
			try { 
				user = DBUtils.findUser(conn, client.getUserName());
			} catch (SQLException e1) {//Current user does not exist, big error, abort
				hasError = true;
				errorString += "INTERNAL SQL ERROR!";
				e1.printStackTrace();
			}

			if(user == null) {
				hasError = true;
				errorString += "INTERNAL ERROR!";

			}else {
				
				int clientId = 0;
				
				try {
					clientId = DBUtils.getClientId(conn, client.getUserName());
					DBUtils.reviewClient(conn, bookingId);//Add the review to the database
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				try {
					DBUtils.addPropertyReview(conn, clientId, DBUtils.getPropertyId(conn, name), comment, score);
					String message = "Client " + client.getUserName() + " has added a new review for your property " + name + ".owner=" + DBUtils.getPropertyOwner(conn, DBUtils.getPropertyId(conn, name)) +"property=" + name;
					request.setAttribute("newPropertyReviewNotification", message);//Trigger the request attribute listener in order to notify the owner of the new review
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				response.sendRedirect(request.getContextPath() + "/viewBookings");//Go back to the page that led here on success
			}

		if(hasError) {
			request.setAttribute("errorString", errorString);

			// Forward to /WEB-INF/views/login.jsp
			RequestDispatcher dispatcher //
			= this.getServletContext().getRequestDispatcher("/WEB-INF/views/viewBookings.jsp");//Failed, go back to the list of bookings

			dispatcher.forward(request, response);
		}
	}

}