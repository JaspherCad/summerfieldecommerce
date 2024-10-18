package com.example.hoteltest.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Room {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String roomType;
	private BigDecimal roomPrice;
	private List<String> photoUrl = new ArrayList<String>();
	private String roomDescription;
	
	

	//if i delete room, delete all bookings with room
	
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room", cascade = CascadeType.ALL)
    List<Booking> bookings = new ArrayList<Booking>();




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




	public List<Booking> getBookings() {
		return bookings;
	}




	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}




	@Override
	public String toString() {
		return "Room [id=" + id + ", roomType=" + roomType + ", roomPrice=" + roomPrice + ", photoUrl=" + photoUrl
				+ ", roomDescription=" + roomDescription +"]";
	}
    
    
	
}
