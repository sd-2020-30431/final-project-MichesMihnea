package com.in28minutes.springboot.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.in28minutes.springboot.web.service.NotificationEvent;
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
import com.srccodes.beans.Admin;
import com.srccodes.beans.Booking;
import com.srccodes.beans.Car;
import com.srccodes.beans.Guest;
import com.srccodes.beans.Notification;
import com.srccodes.beans.Owner;
import com.srccodes.beans.Photo;
import com.srccodes.beans.Review;
import com.srccodes.beans.User;
import com.srccodes.beans.Vehicle;

@SpringBootApplication(scanBasePackages = "com")
@EntityScan("com.srccodes.beans")
@EnableJpaRepositories("com.repos")

public class SpringBootFirstWebApplication implements CommandLineRunner{
	
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
	
	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(SpringBootFirstWebApplication.class);
    	builder.headless(false);
    	ConfigurableApplicationContext context = builder.run(args);
	}
	
	public void run(String... args) throws Exception 
    {       
		
		
    }
}
