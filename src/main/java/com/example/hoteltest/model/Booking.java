package com.example.hoteltest.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "bookings")
public class Booking {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(nullable = false)
	private LocalDate checkInDate;
	
	@Future(message = "Check-out date should be in the future")
	@Column(nullable = false)
	private LocalDate checkOutDate;

	@Min(value = 1, message = "Must be greater than 0")
	@Column(nullable = false)
	private int adultCount;
	
	@Min(value = 0, message = "Child count cannot be negative")
	private int childCount;
	
	@Column(nullable = false)
	private int totalGuestCount;
	
	@Column(nullable = false, unique = true) // Ensures that each booking has a unique confirmation code
	private String confirmationCode;
	
	@ManyToOne(fetch = FetchType.LAZY) // Changed to lazy loading to optimize performance
	@JoinColumn(name = "user_id", nullable = false) // User should not be nullable
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY) // Changed to lazy loading to optimize performance
	@JoinColumn(name = "room_id", nullable = false) // Room should not be nullable
	private Room room;

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public int getAdultCount() {
		return adultCount;
	}

	public void setAdultCount(int adultCount) {
		this.adultCount = adultCount;
	}

	public int getChildCount() {
		return childCount;
	}

	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}

	public int getTotalGuestCount() {
		return totalGuestCount;
	}

	public void setTotalGuestCount(int totalGuestCount) {
		this.totalGuestCount = totalGuestCount;
	}

	public String getConfirmationCode() {
		return confirmationCode;
	}

	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@Override
	public String toString() {
		return "Booking [id=" + id + ", checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate 
               + ", adultCount=" + adultCount + ", childCount=" + childCount 
               + ", totalGuestCount=" + totalGuestCount + ", confirmationCode=" + confirmationCode 
               + ", user=" + user.getId() + "]"; // Adjust to not print full user details
	}
}
