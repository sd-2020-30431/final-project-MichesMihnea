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

@WebServlet(urlPatterns = { "/editProperty"})
/*
 * This servlet holds the web page used to edit a property's details
 * The post method handles the actual modifications in the database
 */
@MultipartConfig

public class EditPropertyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditPropertyServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Get url parameters and set them
		HttpSession session = request.getSession();
		String name = request.getParameter("name");
		request.setAttribute("propertyId", name);
	    Connection conn = MyUtils.getStoredConnection(request);
	    try {
			request.setAttribute("editPropertyName", DBUtils.getPropertyName(conn, Integer.parseInt(name)));
		} catch (NumberFormatException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
			request.setAttribute("editPropertyAddress", DBUtils.getPropertyAddress(conn, DBUtils.getPropertyName(conn, Integer.parseInt(name))));
			request.setAttribute("editPropertyPrice", DBUtils.getPropertyPrice(conn, DBUtils.getPropertyName(conn, Integer.parseInt(name))));
	    }catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    
		// Forward to /WEB-INF/views/homeView.jsp
		// (Users can not access directly into JSP pages placed in WEB-INF)
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/editProperty.jsp");

		dispatcher.forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//Get the new data
		String name = request.getParameter("newName");
		String address = request.getParameter("newAddress");
		float price = Float.parseFloat(request.getParameter("newPrice"));
		HttpSession session = request.getSession();
		User owner = MyUtils.getLoginedUser(session);

		if(owner == null) {//Session has expired, redirect to login screen
			session.setAttribute("resetPasswordErrorString", "Something went wrong!");
			RequestDispatcher dispatcher //
			= this.getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp");

			dispatcher.forward(request, response);

			return;
		}

		boolean hasError = false;
		String errorString = "";

		if (name == null || address == null || name.length() == 0 || address.length() == 0) {//Input constraints
			hasError = true;
			errorString = "Required name and address!";
		}else {
			
			
			Connection conn = MyUtils.getStoredConnection(request);

			User user = null;
			try { 
				user = DBUtils.findUser(conn, owner.getUserName());
			} catch (SQLException e1) {
				hasError = true;
				errorString += "INTERNAL SQL ERROR!";
				e1.printStackTrace();
			}

			if(user == null) {
				hasError = true;
				errorString += "INTERNAL ERROR!";
				//Could not find the current user, this is a serious error, abandon ship
			}else {
				
				int ownerId = 0;
				
				try {
					ownerId = DBUtils.getOwnerId(conn, owner.getUserName());
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				int propertyId = 0;
				propertyId = (int) session.getAttribute("propertyId");

				try {
					DBUtils.updateProperty(conn, propertyId, name, address, price);//Perform the actual update
					System.out.println("UPDATING PROPERTY " + propertyId + "WITH NAME " + name + "AND ADDRESS " + address);
					request.setAttribute("propertyNewName", name);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				/*try {

       //DBUtils.addUser(conn, userName, password, email, type);
       }catch (SQLException e) {
    	   hasError = true;
    	   errorString = e.getMessage();
    	   e.printStackTrace();
       }*/
				response.sendRedirect(request.getContextPath() + "/property?name=" + propertyId);//Success, go to the property's page
			}
		}

		if(hasError) {
			int propertyId = 0;
			propertyId = (int) session.getAttribute("propertyId");
			request.setAttribute("errorString", errorString);

			// Forward to /WEB-INF/views/login.jsp
			response.sendRedirect(request.getContextPath() + "/editProperty?name=" + propertyId);//Failed, try again
		}
	}

}