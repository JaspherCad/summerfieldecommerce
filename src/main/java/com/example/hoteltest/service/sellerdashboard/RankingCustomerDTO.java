package com.example.hoteltest.service.sellerdashboard;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RankingCustomerDTO {

	
	
//	COUNT returns Long.
//
//	• MAX, MIN return the type of the state field to which they are applied.
//
//	• AVG returns Double.
	
	
	private Long id;
	private String fullName;
	private String email;
	
    private Double doubleDigit;

	
	
    private Long longDigit;
    private BigDecimal bigDecimalDigit;
	private LocalDateTime dateFormat;
	
	
	
	public LocalDateTime getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(LocalDateTime dateFormat) {
		this.dateFormat = dateFormat;
	}
	public BigDecimal getBigDecimalDigit() {
		return bigDecimalDigit;
	}
	public void setBigDecimalDigit(BigDecimal bigDecimalDigit) {
		this.bigDecimalDigit = bigDecimalDigit;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
	public Long getLongDigit() {
		return longDigit;
	}
	public void setLongDigit(Long longDigit) {
		this.longDigit = longDigit;
	}
	
	public Long getOrderCount() {
		return longDigit;
	}
	public void setOrderCount(Long longDigit) {
		this.longDigit = longDigit;
	}
	
	
	//default ranking
	public RankingCustomerDTO(Long id, String fullName, String email) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
	}
	
	
	
	public RankingCustomerDTO() {
		super();
	}
	public Double getDoubleDigit() {
		return doubleDigit;
	}
	public void setDoubleDigit(Double doubleDigit) {
		this.doubleDigit = doubleDigit;
	}
	//rank by orderCount or total revenue recieved from them (BOTH LONG)
	public RankingCustomerDTO(Long id, String fullName, String email, Long longDigit) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.longDigit = longDigit;
	}
	//if required AVG(double)
	public RankingCustomerDTO(Long id, String fullName, String email, Double doubleDigit) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.doubleDigit = doubleDigit;
	}
	public RankingCustomerDTO(Long id, String fullName, String email, BigDecimal bigDecimalDigit) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.bigDecimalDigit = bigDecimalDigit;
	}
	public RankingCustomerDTO(Long id, String fullName, String email, LocalDateTime dateFormat) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.dateFormat = dateFormat;
	}
	
	
	
	
	
	
	
	//
	
	
	
	
}
