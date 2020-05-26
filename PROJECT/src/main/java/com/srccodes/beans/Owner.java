package com.srccodes.beans;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="owner")
public class Owner {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long ownerId;
	
	@Column(name="approval")
	private String approval;
	
	@OneToOne(fetch = FetchType.LAZY)
    @MapsId
	private User user;
	
	@OneToMany(mappedBy="owner")
    private Set <Vehicle> vehicles = new HashSet <Vehicle> ();
	
	@OneToMany(mappedBy="owner")
    private Set <Notification> notifications = new HashSet <Notification> ();

	public Owner() {
		
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
	
	public void addVehicle(Vehicle vehicle) {
		vehicles.add(vehicle);
	}
	
	public Set <Vehicle> getVehicles(){
		return vehicles;
	}

	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}
	
	public Set<Notification> getNotifications() {
		return notifications;
	}

	public void addNotification(Notification notification) {
		this.notifications.add(notification);
	}
}
