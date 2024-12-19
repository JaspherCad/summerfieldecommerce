package com.example.hoteltest.authapi;


public class RefreshTokenRequestDTO {
    private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public RefreshTokenRequestDTO(String token) {
		super();
		this.token = token;
	}

	public RefreshTokenRequestDTO() {
		super();
	}
    
	
	
}