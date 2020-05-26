package com.srccodes.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

@WebServlet(urlPatterns = { "/search"})
/*
 * This servlet holds the page that performs the property search, probably the main functionality of this application
 * Everything is handled by JS
 */
@MultipartConfig

public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SearchServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//Get user input, the constraints are checked in the HomeServlet
		String location = request.getParameter("location");
		String checkIn = request.getParameter("checkin");
		String checkOut = request.getParameter("checkout");
		String guests = request.getParameter("guests");
		request.setAttribute("location", request.getParameter("location"));
		request.setAttribute("checkin", request.getParameter("checkin"));
		request.setAttribute("checkout", request.getParameter("checkout"));
		request.setAttribute("guests", request.getParameter("guests"));
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/search.jsp");

		dispatcher.forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
}