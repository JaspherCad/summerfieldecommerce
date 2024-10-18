package com.example.hoteltest.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class RoomDTO {

	private Long id;
	
	private String roomType;
	private BigDecimal roomPrice;
	private List<String> photoUrl = new ArrayList<String>();
	private String roomDescription;
	
    List<BookingsDto> bookings;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public BigDecimal getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(BigDecimal roomPrice) {
		this.roomPrice = roomPrice;
	}

	public List<String> getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(List<String> photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getRoomDescription() {
		return roomDescription;
	}

	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}

	public List<BookingsDto> getBookings() {
		return bookings;
	}

	public void setBookings(List<BookingsDto> bookings) {
		this.bookings = bookings;
	}

	@Override
	public String toString() {
		return "RoomDTO [id=" + id + ", roomType=" + roomType + ", roomPrice=" + roomPrice + ", photoUrl=" + photoUrl
				+ ", roomDescription=" + roomDescription + ", bookings=" + bookings + "]";
	}

    
    
}

