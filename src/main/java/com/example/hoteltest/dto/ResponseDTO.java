package com.example.hoteltest.dto;

import java.util.List;

public class ResponseDTO {

	private int statusCode;
	private String message;
	private String token;
	private String role;
	private String expirationTime;
	private String bookingCOnfirmationCOde;
	private UserDTO user;
	private RoomDTO room;
	private BookingsDto booking;
	private List<UserDTO> userList;
	private List<RoomDTO> roomList;
	private List<BookingsDto> bookingsList;
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}
	public String getBookingCOnfirmationCOde() {
		return bookingCOnfirmationCOde;
	}
	public void setBookingCOnfirmationCOde(String bookingCOnfirmationCOde) {
		this.bookingCOnfirmationCOde = bookingCOnfirmationCOde;
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
	public BookingsDto getBooking() {
		return booking;
	}
	public void setBooking(BookingsDto booking) {
		this.booking = booking;
	}
	public List<UserDTO> getUserList() {
		return userList;
	}
	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
	}
	public List<RoomDTO> getRoomList() {
		return roomList;
	}
	public void setRoomList(List<RoomDTO> roomList) {
		this.roomList = roomList;
	}
	public List<BookingsDto> getBookingsList() {
		return bookingsList;
	}
	public void setBookingsList(List<BookingsDto> bookingsList) {
		this.bookingsList = bookingsList;
	}
	@Override
	public String toString() {
		return "ResponseDTO [statusCode=" + statusCode + ", message=" + message + ", token=" + token + ", role=" + role
				+ ", expirationTime=" + expirationTime + ", bookingCOnfirmationCOde=" + bookingCOnfirmationCOde
				+ ", user=" + user + ", room=" + room + ", booking=" + booking + ", userList=" + userList
				+ ", roomList=" + roomList + ", bookingsList=" + bookingsList + "]";
	}


	





}
