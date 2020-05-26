package com.in28minutes.springboot.web;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;
import java.util.Optional;

//import org.omg.PortableInterceptor.ClientRequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.repos.AdminRepository;
import com.repos.BookingRepository;
import com.repos.CarRepository;
import com.repos.GuestRepository;
import com.repos.NotificationRepository;
import com.repos.OwnerRepository;
import com.repos.PhotoRepository;
import com.repos.ReviewRepository;
import com.repos.UserRepository;
import com.repos.VehicleRepository;
import com.srccodes.beans.Booking;
import com.srccodes.beans.Car;
import com.srccodes.beans.Guest;
import com.srccodes.beans.Notification;
import com.srccodes.beans.Owner;
import com.srccodes.beans.Photo;
import com.srccodes.beans.Review;
import com.srccodes.beans.User;
import com.srccodes.beans.Vehicle;

@Repository
public class DBUtils {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	GuestRepository guestRepository;
	
	@Autowired
	OwnerRepository ownerRepository;
	
	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	VehicleRepository vehicleRepository;
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	CarRepository carRepository;
	
	@Autowired
	PhotoRepository photoRepository;
	
	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	NotificationRepository notificationRepository;
	
	public DBUtils() {
		
	}
	
	public void flush() {
		userRepository.flush();
		guestRepository.flush();
		ownerRepository.flush();
		adminRepository.flush();
		vehicleRepository.flush();
		bookingRepository.flush();
		reviewRepository.flush();
		carRepository.flush();
		photoRepository.flush();
		bookingRepository.flush();
		notificationRepository.flush();
	}
	
	public User getUser(Long userId) {
		return userRepository.findById(userId).get();
	}
	
	public User findUser(String userName, String password) {
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setEmail(null);
		
		Example <User> example = Example.of(user);
		
		Iterable<User> users = userRepository.findAll(example);
		
		Iterator<User> foundUser = users.iterator();
		
		if(foundUser.hasNext()) {
			return foundUser.next();
		}
		else return null;
		
	}
	
	public void addUser(User user) {
		if(user.getType().equals("guest")) {

			Guest guest = new Guest();
			guest.setUser(user);
			guestRepository.save(guest);
		}
		else if(user.getType().equals("owner")) {

			Owner owner = new Owner();
			owner.setUser(user);
			owner.setApproval("automatic");
			ownerRepository.save(owner);
		}
	}
	
	public String getApproval(User user) {
		List <Owner> owners = ownerRepository.findAll();
		Iterator <Owner> it = owners.iterator();
		
		while(it.hasNext()) {
			Owner currentOwner = it.next();
			
			if(currentOwner.getUser().getUserId() - user.getUserId() == 0) {
				return currentOwner.getApproval();
			}
		}
		
		return null;
	}
	
	public void updateApproval(User user, String approval) {

		List <Owner> owners = ownerRepository.findAll();
		Iterator <Owner> it = owners.iterator();
		
		while(it.hasNext()) {
			Owner currentOwner = it.next();
			currentOwner.setApproval("automatic");

			if(currentOwner.getUser().getUserId() - user.getUserId() == 0) {
				currentOwner.setApproval(approval);
				ownerRepository.flush();
				return;
			}
		}
	}
	
	public void updatePassword(User user, String password) {
		
		List <User> users = userRepository.findAll();
		Iterator <User> it = users.iterator();
		
		while(it.hasNext()) {
			User currentUser = it.next();
			if(currentUser.getUserId() == user.getUserId()) {
				currentUser.setPassword(password);
				userRepository.flush();
				return;
			}
		}
	}
	
	public void updateEmail(User user, String email) {
		
		List <User> users = userRepository.findAll();
		Iterator <User> it = users.iterator();
		
		while(it.hasNext()) {
			User currentUser = it.next();
			if(currentUser.getUserId() == user.getUserId()) {
				currentUser.setEmail(email);
				userRepository.flush();
				return;
			}
		}
	}
	
	public void updateSession(ModelMap model, User user) {
		
		List <User> users = userRepository.findAll();
		Iterator <User> it = users.iterator();
		
		while(it.hasNext()) {
			User currentUser = it.next();
			if(currentUser.getUserId() == user.getUserId()) {
				model.put("user", currentUser);
				return;
			}
		}
	}
	
	public void addPhoto(Photo photo, Vehicle vehicle) {
		
		photo.setVehicle(vehicle);
		vehicle.addPhoto(photo);
		photoRepository.save(photo);
	}
	
	public List<Vehicle> getVehicles(User user) {
		
		Vehicle vehicle = new Vehicle();
		
		List <Owner> owners = ownerRepository.findAll();
		Iterator <Owner> it = owners.iterator();
		Owner vehicleOwner = null;
		
		while(it.hasNext()) {
			Owner currentOwner = it.next();
			
			if(currentOwner.getUser().getUserId() - user.getUserId() == 0) {
				vehicleOwner = currentOwner;
				break;
			}
		}
		
		vehicle.setOwner(vehicleOwner);
		vehicle.setApproved(null);
		vehicle.setAvailable(null);

		Example <Vehicle> example = Example.of(vehicle);
		
		Iterable <Vehicle> vehicles = vehicleRepository.findAll(example);
		
		Iterator<Vehicle> foundVehicle = vehicles.iterator();
		
		List<Vehicle> vehicleList = new ArrayList <Vehicle> ();
		
		while(foundVehicle.hasNext()) {
			vehicleList.add(foundVehicle.next());
		}
		
		return vehicleList;

	}
	
	public Owner getOwner(User user) {
		
		List <Owner> owners = ownerRepository.findAll();
		Iterator <Owner> it = owners.iterator();
		
		while(it.hasNext()) {
			Owner currentOwner = it.next();
			
			if(currentOwner.getUser().getUserId() - user.getUserId() == 0) {
				return currentOwner;
			}
		}
		
		return null;
	}
	
	public Guest getGuest(User user) {
		
		List <Guest> guests = guestRepository.findAll();
		Iterator <Guest> it = guests.iterator();
		
		while(it.hasNext()) {
			Guest currentGuest = it.next();
			
			if(currentGuest.getUser().getUserId() - user.getUserId() == 0) {
				return currentGuest;
			}
		}
		
		return null;
	}
	
	public Car addCar(Owner owner, Car car) {
		car.setOwner(owner);
		owner.addVehicle(car);
		ownerRepository.flush();
		carRepository.save(car);
		return car;
	}
	
	public Vehicle getVehicle(Long vehicleId) {
		Optional <Vehicle> vehicle = vehicleRepository.findById(vehicleId);
		return  vehicle.get();
	}
	
	public List <Photo> getPhotos(Vehicle vehicle){
		List <Photo> photoList = new ArrayList <Photo> ();
		Photo photo = new Photo();
		photo.setVehicle(vehicle);
		
		List <Photo> photos = photoRepository.findAll();
		Iterator <Photo> foundPhoto = photos.iterator();
		
		while(foundPhoto.hasNext()) {
			Photo currentPhoto = foundPhoto.next();
			
			if(currentPhoto.getVehicle().getVehicleId() == vehicle.getVehicleId()) {
			photoList.add(currentPhoto);
			}
		}
		
		return photoList;
	}
	
	public void deletePhoto(Long photoId) {
		photoRepository.deleteById(photoId);
	}
	
	public void deleteReview(Long reviewId) {
		reviewRepository.deleteById(reviewId);
	}
	
	public void updatePrice(Vehicle vehicle, Float price) {
		vehicle.setPrice(price);
		vehicleRepository.flush();
	}
	
	public void updateCoords(Vehicle vehicle, Float lat, Float lng) {
		vehicle.setLat(lat);
		vehicle.setLng(lng);
		vehicleRepository.flush();
	}
	
	public void updateAvailability(Vehicle vehicle, Integer available) {
		vehicle.setAvailable(available);
		vehicleRepository.flush();
	}
	
	public List <Vehicle> getAllVehicles(){
		
		Vehicle vehicle = new Vehicle();
		vehicle.setApproved(1);
		vehicle.setAvailable(1);
		
		Example <Vehicle> example = Example.of(vehicle);
		
		Iterable <Vehicle> vehicles = vehicleRepository.findAll(example);
		
		Iterator <Vehicle> foundVehicle = vehicles.iterator();
		
		List <Vehicle> vehicleList = new ArrayList <Vehicle> ();
		
		while(foundVehicle.hasNext()) {
			vehicleList.add(foundVehicle.next());
		}
		
		return vehicleList;

		
	}
	
	public void addBooking(Guest guest, Vehicle vehicle, Booking booking) {
		guest.addBooking(booking);
		vehicle.addBooking(booking);
		bookingRepository.save(booking);
		vehicleRepository.flush();
		guestRepository.flush();
	}
	
	public List <Booking> getBookings(Guest guest){
		
		List <Booking> bookings = new ArrayList <Booking> ();
		List <Booking> allBookings = bookingRepository.findAll();
		Iterator <Booking> it = allBookings.iterator();
		
		while(it.hasNext()) {
			Booking currentBooking = it.next();
			
			if(currentBooking.getGuest() != null) {			
				if(currentBooking.getGuest().getUser().getUserId() == guest.getUser().getUserId()) {
					bookings.add(currentBooking);
				}
			}
		}
		
		return bookings;
	}
	
	public List <Booking> getBookings(Owner owner){
		
		List <Booking> bookings = new ArrayList <Booking> ();
		List <Booking> allBookings = bookingRepository.findAll();
		Iterator <Booking> it = allBookings.iterator();
		
		while(it.hasNext()) {
			Booking currentBooking = it.next();
			
			if(currentBooking.getVehicle() != null) {			
				if(currentBooking.getVehicle().getOwner().getUser().getUserId() == owner.getUser().getUserId()) {
					bookings.add(currentBooking);
				}
			}
		}
		
		return bookings;
	}
	
	public List <Booking> getBookings(Vehicle vehicle){
		
		List <Booking> bookings = new ArrayList <Booking> ();
		List <Booking> allBookings = bookingRepository.findAll();
		Iterator <Booking> it = allBookings.iterator();
		
		while(it.hasNext()) {
			Booking currentBooking = it.next();
			
			if(currentBooking.getVehicle() != null) {			
				if(currentBooking.getVehicle().getVehicleId() == vehicle.getVehicleId()) {
					bookings.add(currentBooking);
				}
			}
		}
		
		return bookings;
	}
	
	public void addNotification(Notification notification) {
		notificationRepository.save(notification);
	}
	
	public List <Notification> getNotifications(User user){
		
		List <Notification> notifications = new ArrayList <Notification> ();
		List <Notification> allNotifications = notificationRepository.findAll();
		Iterator <Notification> it = allNotifications.iterator();
		
		while(it.hasNext()) {
			Notification currentNotification = it.next();
			
			if(user.getType().equals("admin") && currentNotification.getUser().getType() != null) {
				if(currentNotification.getUser().getType().equals("admin"))
					notifications.add(currentNotification);
			}
			else 
			if(currentNotification.getUser().getUserId() - user.getUserId() == 0) {
				notifications.add(currentNotification);
			}
		}
		
		return notifications;
	}
	
	public void deleteNotification(Long notificationId) {
		notificationRepository.deleteById(notificationId);
		this.flush();
	}
	
	public void approveBooking(Notification notification) {
		notification.getBooking().setApproved(1);
		this.flush();
	}
	
	public void approveVehicle(Notification notification) {
		notification.getVehicle().setApproved(1);
		this.flush();
	}
	
	public void deleteVehicle(Vehicle vehicle) {
		vehicleRepository.delete(vehicle);
	}
	
	public Notification getNotification (Long notificationId) {
		return notificationRepository.findById(notificationId).get();
	}
	
	public Booking getBooking (Long bookingId) {
		return bookingRepository.findById(bookingId).get();
	}
	
	public User getAdmin() {
		
		List <User> users = userRepository.findAll();
		Iterator <User> it = users.iterator();
		
		while(it.hasNext()) {
			User currentUser = it.next();
			
			if(currentUser.getType() != null)
				if(currentUser.getType().equals("admin"))
					return currentUser;
		}
		
		return null;
	}
	
	public void deleteBooking(Booking booking) {
		bookingRepository.deleteById(booking.getBookingId());
	}
	
	public void addReview(Review review) {
		reviewRepository.save(review);
	}
	
	public List <Review> getReviews(User user){
		
		 List <Review> reviews = new ArrayList <Review> ();
		 List <Review> allReviews = reviewRepository.findAll();
		 Iterator <Review> it = allReviews.iterator();
		 
		 while(it.hasNext()) {
			 Review currentReview = it.next();
			 
			 if(currentReview.getReceiver().getUserId() - user.getUserId() == 0)
				 reviews.add(currentReview);
		 }
		 
		 return reviews;
	}
	
	public Float getUserAverage(User user) {
		
		Float result = 0f;
		Float count = 0f;
		
		List <Review> reviews = new ArrayList <Review> ();
		List <Review> allReviews = reviewRepository.findAll();
		Iterator <Review> it = allReviews.iterator();
		 
		while(it.hasNext()) {
			Review currentReview = it.next();
			 
			if(currentReview.getReceiver().getUserId() - user.getUserId() == 0) {
				result += currentReview.getScore();
				count ++;
			}
			
		}
		
		return result / count;
	}

	public Float getVehicleAverage(Vehicle vehicle) {
		
		Float result = 0f;
		Float count = 0f;
		
		List <Review> reviews = new ArrayList <Review> ();
		List <Review> allReviews = reviewRepository.findAll();
		Iterator <Review> it = allReviews.iterator();
		 
		while(it.hasNext()) {
			Review currentReview = it.next();
			 
			if(currentReview.getVehicle().getVehicleId() - vehicle.getVehicleId() == 0) {
				result += currentReview.getScore();
				count ++;
			}
			
		}
		
		return result / count;
	}
	
	public List <Review> getReviews(Vehicle vehicle){
		
		List <Review> reviews = new ArrayList <Review> ();
		List <Review> allReviews = reviewRepository.findAll();
		Iterator <Review> it = allReviews.iterator();
		 
		while(it.hasNext()) {
			Review currentReview = it.next();
			 
			if(currentReview.getVehicle().getVehicleId() - vehicle.getVehicleId() == 0) 
				reviews.add(currentReview);
		}
		 
		return reviews;
	}
	
	public Review getReview(Long reviewId) {
		return reviewRepository.findById(reviewId).get();
	}
	
	public Integer getTotalVehicles() {
		return vehicleRepository.findAll().size();
	}
	
	public Integer getTotalBookings() {
		return bookingRepository.findAll().size();
	}
	
	public Float getAveragePrice() {
		List <Vehicle> vehicles = vehicleRepository.findAll();
		float sum = 0;
		
		for(Vehicle currVehicle : vehicles) {
			sum += currVehicle.getPrice();
		}
		
		return sum / vehicles.size();
	}
	
	public Integer getTotalOwners() {
		return ownerRepository.findAll().size();
	}
	
	public Integer getTotalReviews() {
		return reviewRepository.findAll().size();
	}

}
