package com.example.hoteltest.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.hoteltest.model.Booking;
import com.example.hoteltest.model.User;

public class UserDTO {

	private Long id;
	private String email;
	private String fullName;
	private String phoneNumber;
	private String role;
	private Integer block;
    private Integer lot;
    private String password;

	private List<BookingsDto> listOfBookings = new ArrayList<BookingsDto>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<BookingsDto> getListOfBookings() {
		return listOfBookings;
	}
	public void setListOfBookings(List<BookingsDto> listOfBookings) {
		this.listOfBookings = listOfBookings;
	}
	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", email=" + email + ", name=" + fullName + ", phoneNumber=" + phoneNumber + ", role="
				+ role + ", listOfBookings=" + listOfBookings + "]";
	}
	public Integer getBlock() {
		return block;
	}
	public void setBlock(Integer block) {
		this.block = block;
	}
	public Integer getLot() {
		return lot;
	}
	public void setLot(Integer lot) {
		this.lot = lot;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	
	
	
	public UserDTO() {
	}
	public UserDTO(User u) {
		super();
		this.id = u.getId();
		this.email = u.getEmail();
		this.fullName = u.getFullName();
		this.phoneNumber = u.getPhoneNumber();
		this.role = u.getRole();
		this.block = u.getBlock();
		this.lot = u.getLot();
	}

	
}
