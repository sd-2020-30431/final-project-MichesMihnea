<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>



<!-- This is the New Property page. It is static and only gathers user input, and
	then submits it to the servlet. -->
	
	
	
	
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
       /* Set the size of the div element that contains the map */
      #map {
        height: 400px;  /* The height is 400 pixels */
        width: 50%;  /* The width is the width of the web page */
       }
    </style>
    <style> 
            .myclass { 
            visibility: hidden;
         	}
    </style>
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
    
    <section class="ftco-section bg-light">
    <form method="POST" action="${pageContext.request.contextPath}/newVehicle" enctype="multipart/form-data">
      <div class="container">
        <div class="row justify-content-center mb-4 pb-5">
          <div class="col-md-7 text-center heading-section ftco-animate">
            <h2>List a new vehicle</h2>
          </div>
        </div>
        <div class="container">
                   
                   <div class="row justify-content-center mb-3">
                   		<p style="color: red;">${errorString}</p>
                   </div>
                   <div class="row justify-content-center mb-3">
                   <div class="select-wrap one-half">
                        <select name="makes" id="makes" class="form-control">
							<option value="make"> Make </option>
							<option value="-"> -------------------------------------- </option>
                        </select>
                      </div>
                      </div>
					<div class="row justify-content-center mb-3">
                      <div class="textfield-search one-third col-sm-4 "><input type="text" class="form-control" placeholder="Model" name="models"></div>
					</div>
					<div class="row justify-content-center mb-3">
                   <div class="select-wrap one-half">
                        <select name="years" id="years" class="form-control">
							<option value="year"> Production Year </option>
							<option value="-"> -------------------------------------- </option>
                        </select>
                      </div>
                      </div>
                      <div class="row justify-content-center mb-3">
                   <div class="select-wrap one-half">
                        <select name="fuels" id="fuels" class="form-control">
							<option value="fuel"> Fuel Type </option>
							<option value="-"> -------------------------------------- </option>
                        </select>
                      </div>
                      </div>
					<div class="row justify-content-center mb-3">
               			<div class="textfield-search one-third col-sm-4"><input type="text" class="form-control" placeholder="Power" name="power"></div>
               		</div>
               		<div class="row justify-content-center mb-3">
               			<div class="textfield-search one-third col-sm-4"><input type="text" class="form-control" placeholder="Price per day" name="price"></div>
               		</div>
               		 <div class="row justify-content-center mb-3">
                   <div class="select-wrap one-half">
                        <select name="emissions" id="emissions" class="form-control">
							<option value="emissions"> Emissions </option>
							<option value="-"> -------------------------------------- </option>
							<option value="5"> Euro 5 </option>
							<option value="6"> Euro 6 </option>
							<option value="0"> Not Applicable </option>
                        </select>
                      </div>
                      </div>
                      <div class="row justify-content-center mb-3">
                       <div class="select-wrap one-half">
                        <select name="seats" id="seats" class="form-control">
							<option value="seats"> Number of seats </option>
							<option value="-"> -------------------------------------- </option>
							<option value="2"> 2 </option>
							<option value="4"> 4 </option>
							<option value="5"> 5 </option>
							<option value="4"> 6 </option>
							<option value="5"> 7 </option>
                        </select>
                      </div>
                      </div>
                      </div>
                      <div class="row justify-content-center mb-5 pb-1">
                      	<h4>Where is your vehicle?</h4>
                      </div>
                     <div class="row justify-content-center">
                     <div id="map"></div>
                     </div>
                     <input type="text" id="lat" readonly="yes" name="lat" class="myclass"><br>
            <input type="text" id="lng" readonly="yes" name="lng" class="myclass">
            		<%
            		String makes[] = (String[]) request.getAttribute("makesList");
            		for(String i: makes){
            			request.setAttribute("make", i);
            		%>
                     <script>

						var make = '${make}';
						document.getElementById("makes").innerHTML += "<option value=\"" + make + "\">" + make + "</option>";
						</script>
						<%
            		}
						%>
						
						<%
            		String years[] = (String[]) request.getAttribute("yearsList");
            		for(String i: years){
            			request.setAttribute("year", i);
            		%>
                     <script>

						var year = '${year}';
						document.getElementById("years").innerHTML += "<option value=\"" + year + "\">" + year + "</option>";
						</script>
						<%
            		}
						%>
						
						<%
            		String fuels[] = (String[]) request.getAttribute("fuelsList");
            		for(String i: fuels){
            			request.setAttribute("fuel", i);
            		%>
                     <script>

						var fuel = '${fuel}';
						document.getElementById("fuels").innerHTML += "<option value=\"" + fuel + "\">" + fuel + "</option>";
						</script>
						<%
            		}
						%>
        
        <script type="text/javascript" src="js/mapInput.js"></script>
                    <div class="row justify-content-center mb-5 pb-1">
                        <input type="submit" class="search-submit btn btn-primary" value="Register!">
                      </div>
                      <div class="row justify-content-center mb-5 pb-1">
                      	<h4>Let's add some photos!</h4>
                      </div>
                      <div class="row justify-content-center mb-5 pb-1">
                       <input type="file" name="file" multiple="true"/>
                       </div>
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
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBbMvY_LqkOpW6c3YPH4sn70IAp1Zw8gtQ&callback=initMap">
    </script>

  <script src="js/google-map.js"></script>
  <script src="js/main.js"></script>
    
  </body>
</html>