package com.srccodes.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="photo")
public class Photo {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long photoId;
	
	@ManyToOne
    @JoinColumn(name ="FK_VehicleId")
    private Vehicle vehicle;
	
	@Column(name="path")
	private String path;

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getPhotoId() {
		return photoId;
	}

}
