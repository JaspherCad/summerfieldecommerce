package com.example.hoteltest.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Product {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
    private String category;
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    
    //NEW VALUE DELETE IF ERROR
    @Column(nullable = true)
    private BigDecimal cost; //magkano ginastos sa product. for analysis
    @Column(nullable = true)
    private BigDecimal discount;  // Discount percentage

    private BigDecimal priceAfterDiscount; //is a must
    
    

    									//pag binura ko products, burado lahat ito.
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();
    
    private double averageRating;

    
    public List<Review> getReviews() {
		return reviews;
	}


	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}


	public Double getAverageRating() {
		return averageRating;
	}


	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}


	@ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private Store store; // Link product to store
    
    private boolean isFeatured;

    //cascade all meaning: deleting A  product should remove it from all associated entities
    //This is handled by the CascadeType.ALL option in your @OneToMany relationship. 
    //So when you delete a product, all associated CartItem records will also be 
    //deleted automatically.
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // Allows serialization of CartItem list without infinite recursion
    private List<CartItem> cartItems = new ArrayList<>();
    
    
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable( //simply means we merged these two tables and called it  product_tag
        name = "product_tag",
        joinColumns = 
	        @JoinColumn(name = "product_id"),
	        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    private String imgSrc;
    
//EXAMPLE: product_tag
//    product_id	tag_id
//    1					1
//    1					2
//    1					3
    
    
    
    
    
    
    public Product() {
        this.createdAt = LocalDateTime.now(); // Automatically set creation date
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
	
	

	public BigDecimal getDiscount() {
		return discount;
	}


	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
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

	

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public boolean isFeatured() {
		return isFeatured;
	}


	public void setFeatured(boolean isFeatured) {
		this.isFeatured = isFeatured;
	}


	public String getImgSrc() {
		return imgSrc;
	}


	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}


	


	public BigDecimal getCost() {
		return cost;
	}


	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}


	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}


	public BigDecimal getPriceAfterDiscount() {
		return priceAfterDiscount;
	}


	public void setPriceAfterDiscount(BigDecimal priceAfterDiscount) {
		this.priceAfterDiscount = priceAfterDiscount;
	}
	
	
    
}
