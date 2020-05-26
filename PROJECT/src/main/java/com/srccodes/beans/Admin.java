package com.srccodes.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="admin")
public class Admin {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long adminId;
	
	@OneToOne(fetch = FetchType.LAZY)
    @MapsId
	private User user;
	
	public Admin() {
		
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
	
	
}
