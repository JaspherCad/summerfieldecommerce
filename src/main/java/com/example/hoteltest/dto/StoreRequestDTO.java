package com.example.hoteltest.dto;

import java.time.LocalTime;

public class StoreRequestDTO {
	 	private String name;
	    private String description;
	    private Integer block;
	    private Integer lot;
	    private Long gcashNumber;
	    private String phoneNumber;
	    private LocalTime openingTime;
	    private LocalTime closingTime;
	    private boolean gcash;
	    private boolean doDelivery;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
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
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public LocalTime getOpeningTime() {
			return openingTime;
		}
		public void setOpeningTime(LocalTime openingTime) {
			this.openingTime = openingTime;
		}
		public LocalTime getClosingTime() {
			return closingTime;
		}
		public void setClosingTime(LocalTime closingTime) {
			this.closingTime = closingTime;
		}
		public boolean isGcash() {
			return gcash;
		}
		public void setGcash(boolean gcash) {
			this.gcash = gcash;
		}
		public boolean isDoDelivery() {
			return doDelivery;
		}
		public void setDoDelivery(boolean doDelivery) {
			this.doDelivery = doDelivery;
		}
		public Long getGcashNumber() {
			return gcashNumber;
		}
		public void setGcashNumber(Long gcashNumber) {
			this.gcashNumber = gcashNumber;
		}
	    
	    
	    

}
