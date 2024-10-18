package com.example.hoteltest.dto;

import java.math.BigDecimal;
import java.util.List;

import com.example.hoteltest.model.Store;
import com.example.hoteltest.model.User;

public class ProductResponseDTO {
	private Long id;  // Product ID
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
    private String category;
    private UserDTO seller; // = new UserDto(User user);
    private StoreResponseDTO store;
    private String imgSrc;
    private List<String> tagNames;
    
    
    //MANUALLY DO THIS. USE UR BRAIN
    private boolean gcash;
    private boolean doDelivery;

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
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public ProductResponseDTO() {
		super();
	}
	public UserDTO getUserDto() {
		return seller;
	}
	public void setUserDto(User acceptingUser) {
		this.seller = new UserDTO(acceptingUser);
	}
	public UserDTO getSeller() {
		return seller;
	}
	public void setSeller(UserDTO seller) {
		this.seller = seller;
	}
	public StoreResponseDTO getStore() {
		return store;
	}
	public void setStore(StoreResponseDTO store) {
		this.store = store;
	}
	public List<String> getTagNames() {
		return tagNames;
	}
	public void setTagNames(List<String> tagNames) {
		this.tagNames = tagNames;
	}
	public String getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
    
    
    
    
}
