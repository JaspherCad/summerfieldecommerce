package com.example.hoteltest.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class UserLog {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user; //whom the log belongs to
	
	private String action;
	
	private String description;
	
	private LocalDateTime timeStamp;
	
	private String whoPerformedTheAction;
	public UserLog() {
		
	}
	
	public UserLog(User user, String action, String description, String whoPerformedTheAction) {
        this.user = user;
        this.action = action;
        this.description = description;
        this.timeStamp = LocalDateTime.now();
        this.whoPerformedTheAction = whoPerformedTheAction;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getWhoPerformedTheAction() {
		return whoPerformedTheAction;
	}

	public void setWhoPerformedTheAction(String whoPerformedTheAction) {
		this.whoPerformedTheAction = whoPerformedTheAction;
	}
	
	


}
