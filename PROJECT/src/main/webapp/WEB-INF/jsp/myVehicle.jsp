<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="com.srccodes.utils.MyUtils"%>
<%@page import="com.srccodes.utils.DBUtils"%>
<%@page import="com.srccodes.beans.User"%>
<%@page import="com.srccodes.beans.Photo"%>
<%@page import="com.srccodes.beans.Review"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>



<!-- This is the Property page, more exactly an owner's property page. It holds owner-only options, such as
	the ability to add more photos or edit the details of a property, or view the list of bookings.
	The data is fetched at page creation time using Java code from the database. -->



<html lang="en">
        <link href="https://sachinchoolur.github.io/lightGallery/lightgallery/css/lightgallery.css" rel="stylesheet">

    
        <script src="https://cdn.jsdelivr.net/picturefill/2.3.1/picturefill.min.js"></script>
        <script src="https://cdn.rawgit.com/sachinchoolur/lightgallery.js/master/dist/js/lightgallery.js"></script>
        <script src="https://cdn.rawgit.com/sachinchoolur/lg-pager.js/master/dist/lg-pager.js"></script>
        <script src="https://cdn.rawgit.com/sachinchoolur/lg-autoplay.js/master/dist/lg-autoplay.js"></script>
        <script src="https://cdn.rawgit.com/sachinchoolur/lg-fullscreen.js/master/dist/lg-fullscreen.js"></script>
        <script src="https://cdn.rawgit.com/sachinchoolur/lg-zoom.js/master/dist/lg-zoom.js"></script>
        <script src="https://cdn.rawgit.com/sachinchoolur/lg-hash.js/master/dist/lg-hash.js"></script>
        <script src="https://github.com/sachinchoolur/lg-thumbnail.js/blob/master/src/lg-thumbnail.js"></script>
		<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js"></script>
        
       
<style>
            body{
                background-color: #152836
            }
            .demo-gallery > ul {
                margin-bottom: 0;
            }
            .demo-gallery > ul > li {
                float: left;
                margin-bottom: 15px;
                margin-right: 20px;
                width: 200px;
            }
            .demo-gallery > ul > li a {
                border: 3px solid #FFF;
                border-radius: 3px;
                display: block;
                overflow: hidden;
                position: relative;
                float: left;
            }
            .demo-gallery > ul > li a > img {
                -webkit-transition: -webkit-transform 0.15s ease 0s;
                -moz-transition: -moz-transform 0.15s ease 0s;
                -o-transition: -o-transform 0.15s ease 0s;
                transition: transform 0.15s ease 0s;
                -webkit-transform: scale3d(1, 1, 1);
                transform: scale3d(1, 1, 1);
                height: 100%;
                width: 100%;
            }
            .demo-gallery > ul > li a:hover > img {
                -webkit-transform: scale3d(1.1, 1.1, 1.1);
                transform: scale3d(1.1, 1.1, 1.1);
            }
            .demo-gallery > ul > li a:hover .demo-gallery-poster > img {
                opacity: 1;
            }
            .demo-gallery > ul > li a .demo-gallery-poster {
                background-color: rgba(0, 0, 0, 0.1);
                bottom: 0;
                left: 0;
                position: absolute;
                right: 0;
                top: 0;
                -webkit-transition: background-color 0.15s ease 0s;
                -o-transition: background-color 0.15s ease 0s;
                transition: background-color 0.15s ease 0s;
            }
            .demo-gallery > ul > li a .demo-gallery-poster > img {
                left: 50%;
                margin-left: -10px;
                margin-top: -10px;
                opacity: 0;
                position: absolute;
                top: 50%;
                -webkit-transition: opacity 0.3s ease 0s;
                -o-transition: opacity 0.3s ease 0s;
                transition: opacity 0.3s ease 0s;
            }
            .demo-gallery > ul > li a:hover .demo-gallery-poster {
                background-color: rgba(0, 0, 0, 0.5);
            }
            .demo-gallery .justified-gallery > a > img {
                -webkit-transition: -webkit-transform 0.15s ease 0s;
                -moz-transition: -moz-transform 0.15s ease 0s;
                -o-transition: -o-transform 0.15s ease 0s;
                transition: transform 0.15s ease 0s;
                -webkit-transform: scale3d(1, 1, 1);
                transform: scale3d(1, 1, 1);
                height: 100%;
                width: 100%;
            }
            .demo-gallery .justified-gallery > a:hover > img {
                -webkit-transform: scale3d(1.1, 1.1, 1.1);
                transform: scale3d(1.1, 1.1, 1.1);
            }
            .demo-gallery .justified-gallery > a:hover .demo-gallery-poster > img {
                opacity: 1;
            }
            .demo-gallery .justified-gallery > a .demo-gallery-poster {
                background-color: rgba(0, 0, 0, 0.1);
                bottom: 0;
                left: 0;
                position: absolute;
                right: 0;
                top: 0;
                -webkit-transition: background-color 0.15s ease 0s;
                -o-transition: background-color 0.15s ease 0s;
                transition: background-color 0.15s ease 0s;
            }
            .demo-gallery .justified-gallery > a .demo-gallery-poster > img {
                left: 50%;
                margin-left: -10px;
                margin-top: -10px;
                opacity: 0;
                position: absolute;
                top: 50%;
                -webkit-transition: opacity 0.3s ease 0s;
                -o-transition: opacity 0.3s ease 0s;
                transition: opacity 0.3s ease 0s;
            }
            .demo-gallery .justified-gallery > a:hover .demo-gallery-poster {
                background-color: rgba(0, 0, 0, 0.5);
            }
            .demo-gallery .video .demo-gallery-poster img {
                height: 48px;
                margin-left: -24px;
                margin-top: -24px;
                opacity: 0.8;
                width: 48px;
            }
            .demo-gallery.dark > ul > li a {
                border: 3px solid #04070a;
            }
            .home .demo-gallery {
                padding-bottom: 80px;
            }
        </style>
        <style> 
            .mylist { 
            visibility: hidden;
         	}
    </style>
    <style>
             	.mylist2 { 
            visibility: hidden;
         	}
    </style>
    <style> 
            .mylist3 { 
            visibility: hidden;
         	}
    </style>
    <style> 
            .mylist4 { 
            visibility: hidden;
         	}
    </style>

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
              <h1 class="mb-3">My Account</h1>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!-- END slider -->
    
    <script>
function myFunction(button) {
	var jsAtt12 = '${admin}';
	if(jsAtt12 == 0){
		
 		document.getElementById("uploadSection").innerHTML = "<div class=\"row justify-content-center mb-5 mt-5 pb-1\"> <input type=\"file\" name=\"file\" multiple=\"true\"/></div>";
 		document.getElementById("uploadSection").innerHTML += "<div class=\"row justify-content-center mb-5 pb-1\"> <input type=\"submit\" class=\"search-submit btn btn-primary\" value=\"Upload\"></div>";
  		button.style.visibility = "hidden";
	}
}
</script>
    
    <section class="ftco-section bg-light">
    <form method="POST" action="${pageContext.request.contextPath}/myVehicle?vehicleId=${vehicle.vehicleId}" enctype="multipart/form-data">
      <div class="container">
        <div class="row justify-content-center mb-4 pb-5">
          <div class="col-md-7 text-center heading-section ftco-animate">
            <h2>${propertyName}</h2>
          </div>
        </div>
        <div class="row justify-content-center mb-4 pb-5">
            <p style="color: red;">${uploadError}</p>
        </div>
        <div class="row justify-content-center mb-4 pb-5">
        	<div class="mylist2">
            <p style="color: red;">This vehicle has not yet been approved!</p>
            </div>
        </div>
        <div class="row justify-content-center mb-4 pb-5">
        	<div class="mylist4">
            <p style="color: green;">This vehicle is approved!</p>
            </div>
        </div>
        <div class="row justify-content-center mb-4 pb-5">
            <p style="color: green;">${successString}</p>
        </div>
        <div class="row justify-content-center mb-4 pb-5">
        <div class="col-md-7 text-center heading-section ftco-animate">
        <h4>Make:</h4>
        </div>
          <div class="col-md-7 text-center heading-section ftco-animate">
            <h4 style="color: red;" id="make"></h4>
          </div>
        </div>
        <div class="row justify-content-center mb-4 pb-5">
        <div class="col-md-7 text-center heading-section ftco-animate">
        <h4>Model:</h4>
        </div>
          <div class="col-md-7 text-center heading-section ftco-animate">
            <h4 style="color: red;" id="model"></h4>
          </div>
        </div>
        <div class="row justify-content-center mb-4 pb-5">
        <div class="col-md-7 text-center heading-section ftco-animate">
        <h4>Price:</h4>
        </div>
          <div class="col-md-7 text-center heading-section ftco-animate">
            <h4 style="color: red;" id="price"></h4>
          </div>
        </div>
        
        
        <div class="row justify-content-center mb-3 mt-5">
             <h4>Score:</h4> <h4 id="reviewScore"></h4><h4>${vehicleScore}/10</h4>
		</div>
        


        <div class="demo-gallery">
            <ul id="lightgallery" class="list-unstyled row justify-content-center">
            
                
            </ul>
        </div>
        
		<div class="row justify-content-center mt-5">
         <div id="googleMap" style="width:50%;height:400px;"></div>
        </div>
        <p id="uploadSection">	</p>
        

        <div class="container">
                   <div class="row justify-content-center mb-3 mt-5" id="mybtn">
                   		<div class="mylist">
                   			<button onclick="myFunction(this)" class="search-submit btn btn-primary" type="button" >Add Photos</button>
                   		</div>
		</div>
		</div>
		<div class="row justify-content-center mb-3 mt-5" id="availability">
              
		</div>
		<div class="row justify-content-center mb-3 mt-5" id="view-bookings">
                   		
		</div>
		<div class="row justify-content-center mb-3 mt-5" id="aaron-family">
                   		
		</div>
		
		<div class="row justify-content-center mb-3 mt-5" id="back">
              
		</div>
		
		<section class="ftco-section testimony-section">
      <div class="container">
        <div class="row justify-content-center mb-5 pb-5">
          <div class="col-md-7 text-center heading-section ftco-animate">
            <h2>Guest Reviews</h2>
          </div>
        </div>
        <div class="row ftco-animate">
          <div class="carousel owl-carousel ftco-owl" id="reviews">

          </div>
        </div>
      </div>

    </section>
    
    <%
	List <Review> reviews = (List <Review>) request.getAttribute("reviews");
	for(Review review : reviews){
		request.setAttribute("review", review);
		request.setAttribute("author", review.getUser());
		request.setAttribute("fullAuthorName", review.getUser().getFirstName() + " " + review.getUser().getLastName());
	%>
	
	<script>
	var jsAtt11 = '<a href= ${pageContext.request.contextPath}/viewUserProfile?userId=${author.userId}>${fullAuthorName}</a>';
	var jsAtt12 = '${review.message}';
	var jsAtt13 = '${review.score}';
	var jsAtt14 = '<a href= ${pageContext.request.contextPath}/report?reviewId=${review.reviewId}>Report</a>';
	document.getElementById("reviews").innerHTML += "<div class=\"item text-center\">" + "<div class=\"testimony-wrap p-4 pb-5\">" + 
		"<div class=\"user-img mb-4\" style=\"background-image: url(images/guest.jpg)\" style=\"border: 1px solid red;\"></div>" +
		"<div class=\"text\">" + 
		"<p class=\"name\">" + jsAtt13 + "/10</p>" +
		"<p class=\"mb-5\">" + jsAtt12 + "</p>" +
		"<p class=\"name\">" + jsAtt11 + "</p> <span class=\"position\">${author.type}</span>" +
		"<p class=\"name\">" + jsAtt14 + "</p>" +
		"</div></div></div>";
	</script>
	
	
	
	<%
}

%>
		

		<script>
			var jsAtt11 = '${vehicle.vehicleId}';
			var jsAtt12 = '${admin}';
			var jsAtt13 = '${vehicle.approved}';
			var jsAtt14 = '${vehicle.available}';
			if(jsAtt12 == 0){
				if(jsAtt13 == 0){
					element = document.querySelector('.mylist2');
					element.style.visibility = 'visible';
				}else{
					element = document.querySelector('.mylist4');
					element.style.visibility = 'visible';
				}
				element = document.querySelector('.mylist');
				element.style.visibility = 'visible';
				document.getElementById("view-bookings").innerHTML += "<a href= ${pageContext.request.contextPath}/viewBookings?vehicleId=" + jsAtt11 + "&owner=1>" + "View Bookings" + "</a>";
				document.getElementById("aaron-family").innerHTML += "<a href= ${pageContext.request.contextPath}/editVehicle?vehicleId=" + jsAtt11 + ">" + "Edit details" + "</a>";
				document.getElementById("back").innerHTML += "<a href= ${pageContext.request.contextPath}/viewVehicles> Back to my vehicles </a>";
				if(jsAtt14 == 0){
					document.getElementById("availability").innerHTML += "<a href= ${pageContext.request.contextPath}/changeAvailability?available=1&vehicleId=" + jsAtt11 +"> Turn Availability ON </a>";
				}else if(jsAtt14 == 1){
					document.getElementById("availability").innerHTML += "<a href= ${pageContext.request.contextPath}/changeAvailability?available=0&vehicleId=" + jsAtt11 +"> Turn Availability OFF </a>";

				}
			}else{
				document.getElementById("back").innerHTML += "<a href= ${pageContext.request.contextPath}/adminNotifications> Back to my notifications </a>";
			}
		</script>
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
<script>
var jsAtt1 = '${vehicle.make}';
var jsAtt2 = '${vehicle.model}';
var jsAtt3 = '${vehicle.price}';
document.getElementById("make").innerHTML += jsAtt1;
document.getElementById("model").innerHTML += jsAtt2;
document.getElementById("price").innerHTML += jsAtt3;
</script>
<%
            		List <Photo> photos = (List <Photo>) request.getAttribute("photos");
            		for(Photo i : photos){
            			request.setAttribute("photoLink", i.getPath());
            		%>
 <script>
var jsAtt = '${photoLink}';
document.getElementById("lightgallery").innerHTML += "<li class=\"col-xs-6 col-sm-4 col-md-3\" data-responsive=\"uploads/" + jsAtt + 
"\" data-src=\"uploads/" + jsAtt + "\" data-sub-html=\"<h4>Fading Light</h4><p>asdf</p>\" data-pinterest-text=\"Pin it1\" data-tweet-text=\"share on twitter 1\"><a href=\"\"><img class=\"img-responsive\" src=\"uploads/" +
jsAtt + "\" alt=\"Thumb-1\"></a></li> ";
</script>
<script>
            lightGallery(document.getElementById('lightgallery'));
        </script>
					<%}
	%>

    
   <script>
   var infoWindow;
function myMap() {
var mapProp= {
  center:new google.maps.LatLng('${vehicle.lat}','${vehicle.lng}'),
  zoom:5,
};
var map = new google.maps.Map(document.getElementById("googleMap"),mapProp);
var uluru = new google.maps.LatLng('${vehicle.lat}','${vehicle.lng}');
var marker = new google.maps.Marker({position: uluru, map: map});
marker.setMap(map);
infoWindow = new google.maps.InfoWindow;
if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
      var pos = {
        lat: position.coords.latitude,
        lng: position.coords.longitude
      };

      infoWindow.setPosition(pos);
      infoWindow.setContent('Your location');
      infoWindow.open(map);
    }, function() {
      handleLocationError(true, infoWindow, map.getCenter());
    });
  } else {
    // Browser doesn't support Geolocation
    handleLocationError(false, infoWindow, map.getCenter());
  }
}

function handleLocationError(browserHasGeolocation, infoWindow, pos) {
  infoWindow.setPosition(pos);
  infoWindow.setContent(browserHasGeolocation ?
                        'Error: The Geolocation service failed.' :
                        'Error: Your browser doesn\'t support geolocation.');
  infoWindow.open(map);
}

</script>


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
  <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBbMvY_LqkOpW6c3YPH4sn70IAp1Zw8gtQ&callback=myMap">
    </script>
  <script src="js/google-map.js"></script>
  <script src="js/main.js"></script>
    
  </body>
</html>