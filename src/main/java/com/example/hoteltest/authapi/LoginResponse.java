package com.example.hoteltest.authapi;

public class LoginResponse {
	private String token;

    private long expiresIn;

    private long userId;
    
    private String refreshToken;
    
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

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	
    
    
}
