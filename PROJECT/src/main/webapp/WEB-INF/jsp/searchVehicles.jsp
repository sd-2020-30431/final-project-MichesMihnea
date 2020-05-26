<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.srccodes.utils.MyUtils"%>
<%@page import="com.srccodes.utils.DBUtils"%>
<%@page import="com.srccodes.beans.User"%>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.concurrent.TimeUnit" %>
<%@page import="com.srccodes.beans.Vehicle"%>
<%@page import="com.srccodes.beans.Photo"%>
<%@page import="com.srccodes.beans.Car"%>
<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<!DOCTYPE html>



<!-- This is the property list page. It shows properties that fulfill the search form constraints,
	coming from the home page. This is done with Java code in order to fetch the data from the database,
	and JavaScript in order to display the ads. -->



<html lang="en">
  <head>
    <title>Voyage</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    
    <link href="https://fonts.googleapis.com/css?family=Muli:300,400,600,700" rel="stylesheet">

    <link rel="stylesheet" href="css/open-iconic-bootstrap.min.css">
    <link rel="stylesheet" href="css/animate.css">
    
    <link rel="stylesheet" href="css/owl.carousel.min.css">
    <link rel="stylesheet" href="css/owl.theme.default.min.css">
    <link rel="stylesheet" href="css/magnific-popup.css">

    <link rel="stylesheet" href="css/aos.css">

    <link rel="stylesheet" href="css/ionicons.min.css">

    <link rel="stylesheet" href="css/bootstrap-datepicker.css">
    <link rel="stylesheet" href="css/jquery.timepicker.css">

    
    <link rel="stylesheet" href="css/flaticon.css">
    <link rel="stylesheet" href="css/icomoon.css">
    <link rel="stylesheet" href="css/style.css">
  </head>
  <body>
    
    <nav class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light" id="ftco-navbar">
      <div class="container">
        <a class="navbar-brand" href="index.html">Voyage</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#ftco-nav" aria-controls="ftco-nav" aria-expanded="false" aria-label="Toggle navigation">
          <span class="oi oi-menu"></span> Menu
        </button>

        <div class="collapse navbar-collapse" id="ftco-nav">
          <ul class="navbar-nav ml-auto">
            <li class="nav-item active"><a href="${pageContext.request.contextPath}/index" class="nav-link">Home</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/myAccount" class="nav-link">My Account</a></li>

            <li class="nav-item"><a href="${pageContext.request.contextPath}/about" class="nav-link">About</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/contact" class="nav-link">Contact</a></li>
          </ul>
        </div>
      </div>
    </nav>
    <!-- END nav -->
    
    <section class="home-slider owl-carousel">
      <div class="slider-item" style="background-image: url('images/bg_4.jpg');" data-stellar-background-ratio="0.5">
        <div class="overlay"></div>
        <div class="container">
          <div class="row slider-text align-items-center">
            <div class="col-md-7 col-sm-12 ftco-animate">
              <p class="breadcrumbs"><span class="mr-2"><a href="index.html">Home</a></span> <span>Services</span></p>
              <h1 class="mb-3">Hotels</h1>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!-- END slider -->
    
    <section class="ftco-section bg-light">
    <form method="POST" action="${pageContext.request.contextPath}/newProperty" enctype="multipart/form-data">
      <div class="container">
        <div class="row justify-content-center mb-4 pb-5">
          <div class="row justify-content-center mb-5">
						<h3>Showing vehicles in a range of</h3>
						<div class="row ml-3 mt-1">
							<h4 style="color: red;">${range}</h4>
						</div>
        </div>
        
						</div>
		<div class="container">
		<div class="row justify-content-center mb-4 pb-5">
        <h3> for dates </h3>
					
					<div class="row ml-3 mt-1">
							<h4 style="color: red;">${startDate}</h4>
						</div>
						</div>
		<div class="container">
		<div class="row justify-content-center mb-4 pb-5">
						<h3> to </h3>
						<div class="row ml-3 mt-1">
							<h4 style="color: red;">${endDate}</h4>
						</div>
						</div>
						</div>
						</div>
        <div class="container">
                   
                   <div class="row justify-content-center mb-3">
                   		<p style="color: red;">${errorString}</p>
                   </div>
                   <div class="row no-gutters" id="hotels">

          </div>
                      
        </div>
        <%
       		 List <Vehicle> vehicles = (List <Vehicle>) request.getAttribute("rangeVehicles");
        		List <Photo> photos = (List <Photo>) request.getAttribute("photos");
        		Iterator <Photo> it = photos.iterator();
        		Photo photo = null;
        	for(Vehicle i : vehicles){
        		if(it.hasNext())
        			photo = it.next();
        		request.setAttribute("make", i.getMake());
        		request.setAttribute("available", i.getAvailable());
        		request.setAttribute("approved", i.getApproved());
        		request.setAttribute("model", i.getModel());
        		request.setAttribute("photoLink", photo.getPath());
        		request.setAttribute("checkin", 0);
        		request.setAttribute("checkout", 0);
        		request.setAttribute("price", i.getPrice());
        		request.setAttribute("seats", 0);
        		request.setAttribute("stars", 1);
        		request.setAttribute("id", i.getVehicleId());
        %>
 <script>
 
var jsAtt = '${make}';
var jsAtt2 = '${model}';
var jsAtt3 = '${photoLink}';
var jsAtt4 = '${startDate}';
var jsAtt5 = '${endDate}';
var jsAtt6 = '${price}';
var jsAtt7 = '${seats}';
var jsAtt8 = '${available}';
var jsAtt9 = '${stars}';
var jsAtt10 = '${reviews}';
var jsAtt10 = '${approved}';
var jsAtt12 = '${id}';
if(jsAtt8 == 1){
	if(jsAtt10 == 1){
	document.getElementById("hotels").innerHTML += "<div class=\"col-md-6 col-lg-3 ftco-animate\">"
		+ "<a href=\"${pageContext.request.contextPath}/showVehicle?vehicleId=" + jsAtt12 + "&startDate=" + jsAtt4 + "&endDate=" + jsAtt5 + "&guests=" + jsAtt7 + "&price=" + jsAtt6 + "&search=1" + "\" class=\"block-5\" style=\"background-image: url('uploads/" + jsAtt3 + "');\">" +
	"<div class=\"text\">" + "<span class=\"price\">Euro:" + jsAtt6 + "</span>" + "<h3 class=\"heading\">" + jsAtt + "</h3>" +
	"<div class=\"post-meta\">" + "<span>" + jsAtt2 + "</span>" + "</div>" +
	"<p class=\"star-rate\"><span class=\"icon-star\"></span>"
	+ " <span>Reviews: " + jsAtt10 +  "</span></p>"
	 + "</div></a></div>";
	}
}
</script>
					<%} %>
      </div>
      </form>
    </section>

    <footer class="ftco-footer ftco-bg-dark ftco-section">
      <div class="container">
        <div class="row justify-content-center">
            <p><!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
  Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved 
  <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. --></p>
          </div>
          </div>
    </footer>
    
  

  <!-- loader -->
  <div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px"><circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee"/><circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00"/></svg></div>


  <script src="js/jquery.min.js"></script>
  <script src="js/jquery-migrate-3.0.1.min.js"></script>
  <script src="js/popper.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
  <script src="js/jquery.easing.1.3.js"></script>
  <script src="js/jquery.waypoints.min.js"></script>
  <script src="js/jquery.stellar.min.js"></script>
  <script src="js/owl.carousel.min.js"></script>
  <script src="js/jquery.magnific-popup.min.js"></script>
  <script src="js/aos.js"></script>
  <script src="js/jquery.animateNumber.min.js"></script>
  <script src="js/bootstrap-datepicker.js"></script>
  <script src="js/jquery.timepicker.min.js"></script>
  <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
  <script src="js/google-map.js"></script>
  <script src="js/main.js"></script>
    
  </body>
</html>