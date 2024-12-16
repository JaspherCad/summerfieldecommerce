package com.example.hoteltest.dto;

import java.time.LocalDateTime;

import com.example.hoteltest.model.Review;

public class ReviewDTO {

    private Long id;
    private int rating; 
    private String comment;
    private LocalDateTime createdAt;
    
    private Long userId;
    private String userName;

    private Long productId;
    private String productName;

    // Constructors
    public ReviewDTO() {}

    public ReviewDTO(Review reivew) {
        this.id = reivew.getId();
        this.rating = reivew.getRating();
        this.comment = reivew.getComment();
        this.createdAt = reivew.getCreatedAt();
        this.userId = reivew.getUser().getId();
        this.userName = reivew.getUser().getFullName();
        this.productId = reivew.getProduct().getId();
        this.productName = reivew.getProduct().getName();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
