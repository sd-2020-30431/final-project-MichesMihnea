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

@WebServlet(urlPatterns = { "/deletePhoto"})
/*
 * This url handles the deletion of a photo and holds no web page
 */
@MultipartConfig

public class DeletePhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeletePhotoServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//Get url parameters
		HttpSession session = request.getSession();
		int  id =  Integer.parseInt(request.getParameter("photoId"));
		int pid = Integer.parseInt(request.getParameter("propertyName"));
		String name = null;
	    Connection conn = MyUtils.getStoredConnection(request);
		try {
			name = DBUtils.getPropertyName(conn, pid);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	    try {
			DBUtils.deletePhoto(conn, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.sendRedirect(request.getContextPath() + "/editProperty?name=" + pid);//We came from a property edit page, go back there

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}