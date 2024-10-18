package com.example.hoteltest.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {
	
	
    public Cart(Long id, User user, List<CartItem> cartItems) {
		super();
		this.id = id;
		this.user = user;
		this.cartItems = cartItems;
	}
    public Cart(User user) {
		super();
		
		this.user = user;
	}

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonIgnore // Avoid recursive issue

    private User user; // The buyer owning the cart

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)//delete orphanRemoval = true if issue faced
    @JsonIgnore // Prevents the Cart from serializing cart items directly
    private List<CartItem> cartItems = new ArrayList<>();
    
    
    private BigDecimal cartTotalPrice;
    // Constructors
    public Cart() {
        this.cartItems = new ArrayList<>();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	public BigDecimal getCartTotalPrice() {
		return cartTotalPrice;
	}
	public void setCartTotalPrice(BigDecimal cartTotalPrice) {
		this.cartTotalPrice = cartTotalPrice;
	}

    
    
}
