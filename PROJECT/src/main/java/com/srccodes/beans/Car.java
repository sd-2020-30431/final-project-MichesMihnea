package com.srccodes.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity // This tells Hibernate to make a table out of this class
public class Car extends Vehicle{

	@Column(name = "seats")
	private int seats;
	
	@Column(name = "fuel")
	private String fuel;
	
	@Column(name = "emissions")
	private String emissions;

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public String getFuel() {
		return fuel;
	}

	public void setFuel(String fuel) {
		this.fuel = fuel;
	}

	public String getEmissions() {
		return emissions;
	}

	public void setEmissions(String emissions) {
		this.emissions = emissions;
	}
		
}
