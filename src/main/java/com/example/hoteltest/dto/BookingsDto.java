package com.example.hoteltest.dto;

import java.time.LocalDate;

import com.example.hoteltest.model.Room;
import com.example.hoteltest.model.User;



public class BookingsDto {

	private Long id;
	
	
	
	private LocalDate checkInDate;
	
	
	private LocalDate checkOutDate;

	
	private int adultCount;
	
	
	private int childCount;
	
	
	private int totelGuestCount;
	
	
	private String confirmationCode;
	
	
	//when i call this to backend, i want to see the user
	//but when i call the user, i do not want to see the info about user's booking
	
	private UserDTO user;
	
	
	private RoomDTO room;


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


	public int getTotelGuestCount() {
		return totelGuestCount;
	}


	public void setTotelGuestCount(int totelGuestCount) {
		this.totelGuestCount = totelGuestCount;
	}


	public String getConfirmationCode() {
		return confirmationCode;
	}


	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}


	public UserDTO getUser() {
		return user;
	}


	public void setUser(UserDTO user) {
		this.user = user;
	}


	public RoomDTO getRoom() {
		return room;
	}


	public void setRoom(RoomDTO room) {
		this.room = room;
	}


	public BookingsDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "BookingsDto [id=" + id + ", checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate
				+ ", adultCount=" + adultCount + ", childCount=" + childCount + ", totelGuestCount=" + totelGuestCount
				+ ", confirmationCode=" + confirmationCode + ", user=" + user + ", room=" + room + "]";
	}
	
	
}
