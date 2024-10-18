package com.example.hoteltest.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class LoginUserDTO {

	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String password;
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
	@Override
	public String toString() {
		return "LoginUserDTO [email=" + email + ", password=" + password + "]";
	}
	
	
	
}
