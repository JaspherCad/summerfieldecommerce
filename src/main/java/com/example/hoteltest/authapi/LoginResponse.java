package com.example.hoteltest.authapi;

public class LoginResponse {
	private String token;

    private long expiresIn;

    private long userId;
    
    public String getToken() {
        return token;
    }

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
    
    
}
