package com.example.hoteltest.exceptiom;

public class ExpiredTokenException extends RuntimeException  {
	public ExpiredTokenException(String msg) {
		super(msg);
	}
}
