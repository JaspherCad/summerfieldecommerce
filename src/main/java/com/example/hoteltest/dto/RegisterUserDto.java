package com.example.hoteltest.dto;

public class RegisterUserDto {
	private String email;
    
    private String password;
    
    private String fullName;
    
    private String phoneNumber;
    
    private String role;
    
    private Integer block;
    
    private Integer lot;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public RegisterUserDto() {
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
    
    
}
