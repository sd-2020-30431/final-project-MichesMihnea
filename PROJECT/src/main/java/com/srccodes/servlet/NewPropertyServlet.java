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

@WebServlet(urlPatterns = { "/newProperty"})
/*
 * This servlet holds the page that creates a new property ad
 */
@MultipartConfig

public class NewPropertyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public NewPropertyServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		// Forward to /WEB-INF/views/homeView.jsp
		// (Users can not access directly into JSP pages placed in WEB-INF)
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/newProperty.jsp");

		dispatcher.forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Multiple photos can be uploaded at once, so use a list of Parts
		List<Part> fileParts = request.getParts().stream().filter(part -> "file".equals(part.getName())).collect(Collectors.toList()); // Retrieves <input type="file" name="file" multiple="true">
		List <String> imgList = new ArrayList <String> ();
		
		//Get user input and perform constraint checks

		String name = request.getParameter("name");
		
		boolean hasError = false;
		String errorString = "";

		
		if(name == null) {
			hasError = true;
			errorString = "Required name and address!";
		}
		
		String address = request.getParameter("address");
		
		if(address == null) {
			hasError = true;
			errorString = "Required name and address!";
		}
		
		float price = 0;
		
		try {
			price = Float.parseFloat(request.getParameter("price"));
		}catch (NumberFormatException nfex) {
			hasError = true;
			errorString = "Please enter a price!";
		}
		
		if(price <= 0) {
			hasError = true;
			errorString = "Please enter a valid price!";
		}
		
		float lat = 0, lng = 0;//This is the location we pick using Google Maps API
		
		int stars = Integer.parseInt(request.getParameter("stars"));
		try {
			lat = Float.parseFloat(request.getParameter("lat"));//Here we get coordinates from GMaps
			lng = Float.parseFloat(request.getParameter("lng"));
		}catch (NumberFormatException nfex) {
			hasError = true;
			errorString = "Please enter a location!";
		}
		
		if(!hasError) {
		HttpSession session = request.getSession();
		User owner = MyUtils.getLoginedUser(session);

		if(owner == null) {//Session expired, go back to login screen
			session.setAttribute("resetPasswordErrorString", "Something went wrong!");
			RequestDispatcher dispatcher //
			= this.getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp");

			dispatcher.forward(request, response);

			return;
		}

		if (name == null || address == null || name.length() == 0 || address.length() == 0) {
			hasError = true;
			errorString = "Required name and address!";
		}else {
			Connection conn = MyUtils.getStoredConnection(request);

			User user = null;
			try { 
				user = DBUtils.findUser(conn, owner.getUserName());
			} catch (SQLException e1) {//Current user not found, big error, abort
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
					DBUtils.addProperty(conn, name, address, ownerId, price, stars, lat, lng);//Add the property to the database
					int propertyId = DBUtils.getPropertyId(conn, name);
					String message = "Owner " + owner.getUserName() + " has requested to add a new property ad.owner=" + ownerId + "property=" + propertyId;
					request.setAttribute("newPropertyNotification", message);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				File uploads = new File("C:\\Users\\Miches\\eclipse-workspace\\Booking\\WebContent\\uploads");
				if(fileParts.size() > 1) {
				for (Part filePart : fileParts) {//Now iterate over the photos and upload them, then link them to the property in the database
					//The server has to be refreshed in order to display the photos!
					String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
					InputStream fileContent = filePart.getInputStream();
					File file = File.createTempFile("somefilename-", ".png", uploads);
					imgList.add(file.getName());

					try (InputStream input = filePart.getInputStream()) {
						Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);//Photos get copied to our server
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
				}
				
				int propertyId = 0;
				try {
					propertyId = DBUtils.getPropertyId(conn, name);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Iterator <String> it = imgList.iterator();
				while(it.hasNext()) {
						try {
							DBUtils.addPhoto(conn, propertyId, it.next());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}

				response.sendRedirect(request.getContextPath() + "/myAccount");//Success, go back to the account page
				return;
			}
		}
		}else {
			request.setAttribute("errorString", errorString);

			// Forward to /WEB-INF/views/login.jsp
			RequestDispatcher dispatcher //
			= this.getServletContext().getRequestDispatcher("/WEB-INF/views/newProperty.jsp");//Failed, try again

			dispatcher.forward(request, response);
		}
	}

}