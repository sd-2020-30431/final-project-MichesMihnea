<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>



<!-- This is the Contact page and it is static, there is nothing going on here. -->




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
              <p class="breadcrumbs"><span class="mr-2"><a href="index.html">Home</a></span> <span>Contact</span></p>
              <h1 class="mb-3">Contact</h1>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!-- END slider -->

    <section class="ftco-section contact-section">
      <div class="container">
        <div class="row block-9 mb-4">
          <div class="col-md-6 pr-md-5 flex-column">
            <div class="row d-block flex-row">
              <h2 class="h4 mb-4">Contact Information</h2>
              <div class="col mb-3 d-flex py-4 border" style="background: white;">
                <div class="align-self-center">
                  <p class="mb-0"><span>Address:</span> 198 West 21th Street, Suite 721 New York NY 10016</p>
                </div>
              </div>
              <div class="col mb-3 d-flex py-4 border" style="background: white;">
                <div class="align-self-center">
                  <p class="mb-0"><span>Phone:</span> <a href="tel://1234567920">+ 1235 2355 98</a></p>
                </div>
              </div>
              <div class="col mb-3 d-flex py-4 border" style="background: white;">
                <div class="align-self-center">
                  <p class="mb-0"><span>Email:</span> <a href="mailto:info@yoursite.com">info@yoursite.com</a></p>
                </div>
              </div>
              <div class="col mb-3 d-flex py-4 border" style="background: white;">
                <div class="align-self-center">
                  <p class="mb-0"><span>Website</span> <a href="#">yoursite.com</a></p>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <form action="#">
              <div class="form-group">
                <input type="text" class="form-control" placeholder="Your Name">
              </div>
              <div class="form-group">
                <input type="text" class="form-control" placeholder="Your Email">
              </div>
              <div class="form-group">
                <input type="text" class="form-control" placeholder="Subject">
              </div>
              <div class="form-group">
                <textarea name="" id="" cols="30" rows="7" class="form-control" placeholder="Message"></textarea>
              </div>
              <div class="form-group">
                <input type="submit" value="Send Message" class="btn btn-primary py-3 px-5">
              </div>
            </form>
          </div>
        </div>
        <div class="row justify-content-center">
         <div id="googleMap" style="width:100%;height:400px;"></div>
        </div>
      </div>
    </section>
          <script>
function myMap() {
var mapProp= {
  center:new google.maps.LatLng(45.420314,23.369711),
  zoom:5,
};
var map = new google.maps.Map(document.getElementById("googleMap"),mapProp);
var uluru = new google.maps.LatLng(45.420314,23.369711);
var marker = new google.maps.Marker({position: uluru, map: map});
marker.setMap(map);
}
</script>

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
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBbMvY_LqkOpW6c3YPH4sn70IAp1Zw8gtQ&callback=myMap">
    </script>
  <script src="js/google-map.js"></script>
  <script src="js/main.js"></script>
    
  </body>
</html>