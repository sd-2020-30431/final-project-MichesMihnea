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
<%@page import="java.util.Calendar" %>
<%@page import="java.util.concurrent.TimeUnit" %>
<!DOCTYPE html>
 



<!-- This is the home page of the application. It also contains the property search form, but is otherwise static. -->





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
    <style> 
            .mylist { 
            visibility: hidden;
         	}
    </style>
  </head>
  
  <body>
    
    <nav class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light" id="ftco-navbar">
      <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/index">Voyage</a>
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
      <div class="slider-item" style="background-image: url('images/bg_4.jpg');">
        <div class="overlay"></div>
        <div class="container">
          <div class="row slider-text align-items-center">
            <div class="col-md-7 col-sm-12 ftco-animate">
              <h1 class="mb-3">Experience the best trip ever</h1>
            </div>
          </div>
        </div>
      </div>

      <div class="slider-item" style="background-image: url('images/bg_1.jpg');">
        <div class="overlay"></div>
        <div class="container">
          <div class="row slider-text align-items-center">
            <div class="col-md-7 col-sm-12 ftco-animate">
              <h1 class="mb-3">Making the most out of your holiday</h1>
            </div>
          </div>
        </div>
      </div>

      <div class="slider-item" style="background-image: url('images/bg_3.jpg');">
        <div class="overlay"></div>
        <div class="container">
          <div class="row slider-text align-items-center">
            <div class="col-md-7 col-sm-12 ftco-animate">
              <h1 class="mb-3">Car Rental Just For You</h1>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!-- END slider -->
    

    <div class="ftco-section-search">
      <div class="container">
        <div class="row">
          <div class="col-md-12 tabulation-search">
            <div class="element-animate">
              <div class="nav nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
                <a class="nav-link p-3 active" id="v-pills-home-tab" data-toggle="pill" href="#v-pills-home" role="tab" aria-controls="v-pills-home" aria-selected="true"><span></span> Car</a>
              </div>
            </div>
              
              
            <div class="tab-content py-5" id="v-pills-tabContent">
              <div class="tab-pane fade show active" id="v-pills-home" role="tabpanel" aria-labelledby="v-pills-home-tab">
                <div class="block-17">
                 <form method="POST" action="${pageContext.request.contextPath}/index">
                    <div class="fields d-block d-lg-flex">

                      <div class="textfield-search one-third"><input type="text" id= "range" name="range" class="form-control" placeholder="Search Range"></div>

                       <div class="check-in one-third"><input type="text" id="checkin_date" name="checkin_date" class="form-control" placeholder="Start date"></div>

                      <div class="check-out one-third"><input type="text" id="checkout_date" name="checkout_date" class="form-control" placeholder="End date"></div>
                      
                      <input type="hidden" id="geoLocationLat" name="geoLocationLat" value="12">
                      
                      <input type="hidden" id="geoLocationLng" name="geoLocationLng" value="22">
                      
                      
                      <div class="select-wrap one-third">
                        <div class="icon"><span class="ion-ios-arrow-down"></span></div>
                        <select name="seats" id="seats" class="form-control">
                          <option value="0">Seats</option>
                          <option value="1">2</option>
                          <option value="2">4</option>
                          <option value="3">5</option>
                          <option value="4">6+</option>
                        </select>
                      </div>
                    </div>
                    <input type="submit" class="search-submit btn btn-primary" value="Find Vehicles">  
                  </form>
                </div>
              </div>

            </div>
          </div>
        </div>
      </div>
    </div>
    
    <div class="row justify-content-center">
    <h4 style="color: red;" id="errorStr">${errorString }</h4>
    </div>
    
    <section class="home-slider owl-carousel">
    <div class="row justify-content-center">
    <h4 style="color: red;" id="error" class="mylist"></h4>
    </div>
    </section>
    
    <%
    	session.setAttribute("msg", "HELLO WORLD");
    	if(request.getAttribute("invalidDate") != null){
    		%>
    		<script>
    		element = document.querySelector('.mylist');
               			element.style.visibility = 'visible';
            document.getElementById("error").innerHTML += '${errorString}';
            </script>
    		<%
    	}
    %>
    
    <script>
    	
    </script>
    
    <script>
                      
                
                          if(navigator.geolocation)
                          {
                        	  console.log("ASDFASDFASDFASDFASDFASDFASDFASD");
                        	  navigator.geolocation.getCurrentPosition(showPosition);
                        	 
                             
                          } else {
                              alert("Your browser or device doesn't support Geolocation");
                              
                          }
                      
                      // If we have a successful location update
                      function showPosition(position)
                      {
                    	  document.getElementById("geoLocationLat").value =  position.coords.latitude; 
                          document.getElementById("geoLocationLng").value = position.coords.longitude;

                      }
                      </script>
 
    <section class="ftco-section-2">
      <div class="container-fluid d-flex">
        <div class="section-2-blocks-wrapper row no-gutters">
          <div class="img col-sm-12 col-lg-6" style="background-image: url('images/tour-1.jpg');">
            <a href="https://vimeo.com/45830194" class="button popup-vimeo"><span class="ion-ios-play"></span></a>
          </div>
          <div class="text col-lg-6 ftco-animate">
            <div class="text-inner align-self-start">
              
              <h3>Welcome to Voyage.com!</h3>
              <p>Planning a trip or looking for ideas? Our database of vehicles is at your disposal, together with many reviews from trusted guests.</p>

              <p>Or maybe you are looking to get your vehicle to earn its keep? Feel free to post an ad on our site!</p>
            </div>
          </div>
          
          
        </div>
      </div>
    </section>

   

    <section class="ftco-section bg-light">
      <div class="container">
        <div class="row justify-content-center mb-5 pb-5">
          <div class="col-md-7 text-center heading-section ftco-animate">
            <h2>Our Services</h2>
          </div>
        </div>
        <div class="row">
          <div class="col-md-6 col-lg-3 d-flex align-self-stretch ftco-animate">
            <div class="media block-6 services d-block text-center">
              <div class="d-flex justify-content-center"><div class="icon d-flex justify-content-center mb-3"><span class="align-self-center flaticon-sailboat"></span></div></div>
              <div class="media-body p-2">
                <h3 class="heading">Holiday Planning</h3>
                <p>Browse vehicles all around the world through us!</p>
              </div>
            </div>      
          </div>
          <div class="col-md-6 col-lg-3 d-flex align-self-stretch ftco-animate">
            <div class="media block-6 services d-block text-center">
              <div class="d-flex justify-content-center"><div class="icon d-flex justify-content-center mb-3"><span class="align-self-center flaticon-around"></span></div></div>
              <div class="media-body p-2">
                <h3 class="heading">Real Guest Reviews</h3>
                <p>Reviews from our trusted guests will help you decide!</p>
              </div>
            </div>      
          </div>
          <div class="col-md-6 col-lg-3 d-flex align-self-stretch ftco-animate">
            <div class="media block-6 services d-block text-center">
              <div class="d-flex justify-content-center"><div class="icon d-flex justify-content-center mb-3"><span class="align-self-center flaticon-compass"></span></div></div>
              <div class="media-body p-2">
                <h3 class="heading">Free Cancellation</h3>
                <p>Changed your mind? No problem!</p>
              </div>
            </div>    
          </div>

          <div class="col-md-6 col-lg-3 d-flex align-self-stretch ftco-animate">
            <div class="media block-6 services d-block text-center">
              <div class="d-flex justify-content-center"><div class="icon d-flex justify-content-center mb-3"><span class="align-self-center flaticon-map-of-roads"></span></div></div>
              <div class="media-body p-2">
                <h3 class="heading">Best Price Guarantee</h3>
                <p>We price match any offer you can find.</p>
              </div>
            </div>      
          </div>
        </div>
      </div>
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
  <script src="js/main.js"></script>
    
  </body>
</html>