package com.example.hoteltest.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Store {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String name;

	    @Column(nullable = false)
	    private String description;

	    @Column(nullable = true) 
	    private Long gcashNumber;
	    
	    private Integer block; // Address of the store, optional for now
	    private Integer lot; // Address of the store, optional for now

	    private String phoneNumber;
	    private LocalTime openingTime; // Store's opening time
	    private LocalTime closingTime; // Store's closing time

	    
	    private boolean gcash;
	    private boolean doDelivery;

	    
	    @OneToOne
	    @JoinColumn(name = "user_id", referencedColumnName = "id")
	    private User user; // The seller associated with the store

	    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    @JsonIgnore
	    private List<Product> products = new ArrayList<>();

	    @CreationTimestamp
	    private LocalDateTime createdAt;

	    @UpdateTimestamp
	    private LocalDateTime updatedAt;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

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

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public List<Product> getProducts() {
			return products;
		}

		public void setProducts(List<Product> products) {
			this.products = products;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}

		public LocalDateTime getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(LocalDateTime updatedAt) {
			this.updatedAt = updatedAt;
		}

		public Store() {
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
