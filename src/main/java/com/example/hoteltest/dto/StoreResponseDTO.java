package com.example.hoteltest.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.hoteltest.model.Product;
import com.example.hoteltest.model.Store;
import com.example.hoteltest.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

public class StoreResponseDTO {
	
    private Long id;

    private String name;

    private String description;

    private Integer block; // Address of the store, optional for now
    private Integer lot; // Address of the store, optional for now

    private String phoneNumber;
    private Long gcashNumber;
    private LocalTime openingTime; // Store's opening time
    private LocalTime closingTime; // Store's closing time

  
    private User user; // The seller associated with the store

    private List<ProductDTO> products = new ArrayList<>();

    @JsonProperty("gcash")
    private boolean gcash;

    @JsonProperty("doDelivery")
    private boolean doDelivery;
    
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
 

	public StoreResponseDTO(Store actualStore) {
		super();
		this.id = actualStore.getId();
		this.name = actualStore.getName();
		this.description = actualStore.getDescription();
		this.block = actualStore.getBlock();
		this.lot = actualStore.getLot();
		this.phoneNumber = actualStore.getPhoneNumber();
		this.openingTime = actualStore.getOpeningTime();
		this.closingTime = actualStore.getClosingTime();
		this.gcashNumber = actualStore.getGcashNumber();
		this.user = actualStore.getUser();
		this.createdAt = actualStore.getCreatedAt();
		this.updatedAt = actualStore.getUpdatedAt();
		this.gcash = actualStore.isGcash();
		this.doDelivery = actualStore.isDoDelivery();
		
		System.out.println("Gcash: " + actualStore.isGcash());
        System.out.println("DoDelivery: " + actualStore.isDoDelivery());
		this.products = actualStore.getProducts().stream()
				.map(product -> new ProductDTO(product))
				.collect(Collectors.toList());
		

	}

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

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
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
