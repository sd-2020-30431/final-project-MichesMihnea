<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.srccodes.utils.MyUtils"%>
<%@page import="com.srccodes.utils.DBUtils"%>
<%@page import="com.srccodes.beans.User"%>
<%@page import="com.srccodes.beans.Booking"%>
<%@page import="com.srccodes.beans.Vehicle"%>
<%@page import="java.util.List" %>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>



<!-- This page contains the list of an owner's properties. Data
	is fetched from the database using Java code.  -->



<html lang="en">
<head>
<title>Voyage</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link
	href="https://fonts.googleapis.com/css?family=Muli:300,400,600,700"
	rel="stylesheet">

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
<style> 
            .mylist2 { 
            visibility: hidden;
         	}
    </style>
</head>
<body>

	<nav
		class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light"
		id="ftco-navbar">
		<div class="container">
			<a class="navbar-brand" href="index.html">Voyage</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#ftco-nav" aria-controls="ftco-nav"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="oi oi-menu"></span> Menu
			</button>

			<div class="collapse navbar-collapse" id="ftco-nav">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item active"><a
						href="${pageContext.request.contextPath}/index" class="nav-link">Home</a></li>
					<li class="nav-item"><a
						href="${pageContext.request.contextPath}/myAccount"
						class="nav-link">My Account</a></li>

					<li class="nav-item"><a
						href="${pageContext.request.contextPath}/about" class="nav-link">About</a></li>
					<li class="nav-item"><a
						href="${pageContext.request.contextPath}/contact" class="nav-link">Contact</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<!-- END nav -->

	<section class="home-slider owl-carousel">
		<div class="slider-item"
			style="background-image: url('images/bg_4.jpg');"
			data-stellar-background-ratio="0.5">
			<div class="overlay"></div>
			<div class="container">
				<div class="row slider-text align-items-center">
					<div class="col-md-7 col-sm-12 ftco-animate">
						<p class="breadcrumbs">
							<span class="mr-2"><a href="index.html">Home</a></span> <span>Services</span>
						</p>
						<h1 class="mb-3">My Account</h1>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- END slider -->

	<section class="ftco-section bg-light">
		<form method="POST" action="${pageContext.request.contextPath}/login">
			<div class="container">
			<div class="row justify-content-center mb-4 pb-5">
            <p style="color: red;">${unconfirmedString}</p>
            <p style="color: green;">${successString}</p>
        </div>
				<div class="row justify-content-center mb-4 pb-5">
					<div class="col-md-7 text-center heading-section ftco-animate">
						<h2>Your Bookings</h2>
					</div>
				</div>
				
				<div class="container">

					<div class="row justify-content-center mb-3">
						<div class="row justify-content-center mb-1">
				<ul id="aaron-family">
				</ul>
				
				</div>
				
				
				
				
				
					</div>
					
					<div class="row justify-content-center mb-4 pb-5">
					<div class="col-md-7 text-center heading-section ftco-animate">
						<h2>Past Bookings</h2>
					</div>
				</div>
				<div class="row justify-content-center mb-3">
						<div class="row justify-content-center mb-1">
				<ul id="aaron-family2">
				</ul>
				
				</div>
				
				
				
				
				
					</div>
					
					<div class="row justify-content-center mb-3 mt-5" id="back">
              
		</div>

		
		<script>
			document.getElementById("back").innerHTML += "<a href= ${pageContext.request.contextPath}/myAccount> Back to my account </a>";
		</script>
		<%
            		List <Booking> bookings = (List <Booking>) request.getAttribute("bookings");
            		for(Booking i : bookings){
            			String date1 = new SimpleDateFormat("dd/MM/yyyy").format(i.getStartDate());  
            			String date2 = new SimpleDateFormat("dd/MM/yyyy").format(i.getEndDate());  
            			request.setAttribute("bookingInfo", i.getVehicle().getYear() + " " + i.getVehicle().getMake() + " " + i.getVehicle().getModel() +
            					" from " + date1 + " to " + date2);
            			request.setAttribute("booking", i);
            			if(i.getEndDate().compareTo(new Date(System.currentTimeMillis())) < 0){
            				request.setAttribute("old", 1);
            			}else request.setAttribute("old", 0);
            			
            			request.setAttribute("started", i.getStarted());
            			request.setAttribute("complete", i.getComplete());
            		%>
                     <script>
                     	var owner = '${owner}';
						var info = '${bookingInfo}';
						var id = '${booking.vehicle.vehicleId}';
						var bookingId = '${booking.bookingId}'
						var startDate = '${booking.startDate}';
						var endDate = '${booking.endDate}';
						var old = '${old}';
						var started = '${started}';
						var complete = '${complete}';
						
						if(complete == 1)
							document.getElementById("aaron-family2").innerHTML += "<li><a href= ${pageContext.request.contextPath}/showVehicle?vehicleId="
								+ id + "&bookingId=${booking.bookingId}" + ">" + info + "</a><div class=\"row justify-content-center mb-3\"><a href= ${pageContext.request.contextPath}/newReview?bookingId=" + 
								bookingId + "&bookingId=" + bookingId + ">Leave a review?</a></div></li>";	
						if(old == 0 && owner == 1 && started == 0)
							document.getElementById("aaron-family").innerHTML += "<li><a href= ${pageContext.request.contextPath}/showVehicle?vehicleId="
									+ id + "&bookingId=${booking.bookingId}" + ">" + info + "</a><div class=\"row justify-content-center mb-3\"><a href= ${pageContext.request.contextPath}/startBooking?vehicleId=" + 
											id + "&bookingId=" + bookingId + ">Confirm Pick Up</a></div></li>";
						else if(old == 0 && owner == 1 && started == 1)
							document.getElementById("aaron-family").innerHTML += "<li><a href= ${pageContext.request.contextPath}/showVehicle?vehicleId="
								+ id + "&bookingId=${booking.bookingId}" + ">" + info + "</a></li>";
						else if(old == 0 && owner == 0)
							document.getElementById("aaron-family").innerHTML += "<li><a href= ${pageContext.request.contextPath}/showVehicle?vehicleId="
								+ id + "&bookingId=${booking.bookingId}" + ">" + info + "</a></li>";
						else if(old == 1 && owner == 0)
							document.getElementById("aaron-family2").innerHTML += "<li><a href= ${pageContext.request.contextPath}/showVehicle?vehicleId="
									+ id + "&bookingId=${booking.bookingId}" + ">" + info + "</a></li>";
						else if(complete == 0)
							document.getElementById("aaron-family2").innerHTML += "<li><a href= ${pageContext.request.contextPath}/showVehicle?vehicleId="
								+ id + "&bookingId=${booking.bookingId}" + ">" + info + "</a><div class=\"row justify-content-center mb-3\"><a href= ${pageContext.request.contextPath}/completeBooking?vehicleId=" + 
								id + "&bookingId=" + bookingId + ">Complete</a></div></li>";	
						</script>
						<%
            		}
						%>

					
				</div>

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
	<div id="ftco-loader" class="show fullscreen">
		<svg class="circular" width="48px" height="48px">
			<circle class="path-bg" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke="#eeeeee" />
			<circle class="path" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke-miterlimit="10" stroke="#F96D00" /></svg>
	</div>


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
	<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
	<script src="js/google-map.js"></script>
	<script src="js/main.js"></script>

</body>
</html>