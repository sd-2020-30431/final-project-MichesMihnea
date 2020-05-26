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
 
@WebServlet(urlPatterns = { "/property"})
/*
 * This servlet holds the page showing a property with all its details to the OWNER of the property
 * New photos can be uploaded here and the post method handles that
 */
@MultipartConfig
public class PropertyServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public PropertyServlet() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
 
      //Get url parameters and set them accordingly
	   
       String name = request.getParameter("name");
       int id = Integer.parseInt(name);
       Connection conn = MyUtils.getStoredConnection(request);
       String admin = request.getParameter("admin");
       if(admin != null)
    	   request.setAttribute("admin", 1);
       else request.setAttribute("admin", 0);
       String address = request.getParameter("address");
       try {
		request.setAttribute("propertyName", DBUtils.getPropertyName(conn, id));
		request.setAttribute("propertyId", id);
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
       request.setAttribute("address", address);
       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/property.jsp");
       dispatcher.forward(request, response);
        
   }
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
	   //Multiple new photos can be uploaded, so we use a list of Parts
	   List<Part> fileParts = request.getParts().stream().filter(part -> "file".equals(part.getName())).collect(Collectors.toList()); // Retrieves <input type="file" name="file" multiple="true">
		List <String> imgList = new ArrayList <String> ();

		HttpSession session = request.getSession();
		Connection conn = MyUtils.getStoredConnection(request);
		String name = null;
		int id = (int) session.getAttribute("propertyId");
		try {
			name = DBUtils.getPropertyName(conn, id);
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		User owner = MyUtils.getLoginedUser(session);

		if(owner == null) {//Session has expired, back to login page
			session.setAttribute("resetPasswordErrorString", "Something went wrong!");
			RequestDispatcher dispatcher //
			= this.getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp");

			dispatcher.forward(request, response);

			return;
		}

		boolean hasError = false;
		String errorString = "";

		 {

			User user = null;
			try { 
				user = DBUtils.findUser(conn, owner.getUserName());
			} catch (SQLException e1) {
				hasError = true;
				errorString += "INTERNAL SQL ERROR!";
				e1.printStackTrace();
			}

			if(user == null) {//Current user does not exist, big error, abort
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
				File uploads = new File("C:\\Users\\Miches\\eclipse-workspace\\Booking\\WebContent\\uploads");//Upload path
				for (Part filePart : fileParts) {
					String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
					if(fileName.length() <= 3) {
						session.setAttribute("uploadError", "No files selected!");//Empty upload
						System.out.println("UPLOAD ERROR");
						response.sendRedirect(request.getContextPath() + "/property?name=" + name);//Try again
						return;
					}else
					{
						session.removeAttribute("uploadError");
					}
					InputStream fileContent = filePart.getInputStream();
					File file = File.createTempFile("somefilename-", ".png", uploads);
					imgList.add(file.getName());

					try (InputStream input = filePart.getInputStream()) {
						Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);//Do the actual upload
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
				

				Iterator <String> it = imgList.iterator();
				while(it.hasNext()) {
						try {
							DBUtils.addPhoto(conn, id, it.next());//Update the database
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}


				try {
					response.sendRedirect(request.getContextPath() + "/property?name=" + DBUtils.getPropertyId(conn, name));//Failed, try again
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		if(hasError) {
			request.setAttribute("errorString", errorString);

			// Forward to /WEB-INF/views/login.jsp
			RequestDispatcher dispatcher //
			= this.getServletContext().getRequestDispatcher("/WEB-INF/views/myAccount.jsp");

			dispatcher.forward(request, response);
		}
   }
 
}
