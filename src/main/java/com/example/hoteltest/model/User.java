package com.example.hoteltest.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users") //because user entity is existing BY DEFAULT
public class User implements UserDetails {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
    private String fullName;

    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
    
    private String phoneNumber;
    private String role;
    
    @Column(nullable = true)
    private Integer block;
    
    @Column(nullable = true)
    private Integer lot;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<Booking>();
    
    
    
    
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore // Prevent recursion
    private Cart cart;
    
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    @JsonIgnore // Prevent recursion
    private List<OrderEntity> orders = new ArrayList<>();
    
    
    
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore // Prevent recursion
    private Store store; // Link user to store for sellers
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role));
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * Indicates whether the user is locked or unlocked. A locked user cannot be
	 * authenticated.
	 * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
	 */
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * Indicates whether the user's credentials (password) has expired. Expired
	 * credentials prevent authentication.
	 * @return <code>true</code> if the user's credentials are valid (ie non-expired),
	 * <code>false</code> if no longer valid (ie expired)
	 */
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * Indicates whether the user is enabled or disabled. A disabled user cannot be
	 * authenticated.
	 * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
	 */
	
	@Override
	public boolean isEnabled() {
		return true;
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
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<Booking> getBookings() {
		return bookings;
	}
	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
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
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public List<OrderEntity> getOrders() {
		return orders;
	}
	public void setOrders(List<OrderEntity> orders) {
		this.orders = orders;
	}
	
	
	
	
	
    
    
    
    

}
