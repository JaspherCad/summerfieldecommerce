package com.example.hoteltest.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

//general idea of what i ordered.
@Entity
public class OrderEntity {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<OrderItem> orderItems =  new ArrayList<>();

    
    @ManyToOne
    private User buyer; 
    
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "store_id")
    private Store store;

    private BigDecimal totalPrice;
    @Column(nullable = true)
    private BigDecimal cost;
    private String status; // PENDING, SHIPPED, DELIVERED
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String paymentMethod; //gcash or cash
    
    @Column(nullable = false)
    private String pickupOrDeliver; //pickup or delivery
    // Default constructor
    public OrderEntity() {}

    // Getters and setters (if needed)


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	
	// Add an order item to the order
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this); // Set the order in the order item to ensure the relationship
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrder(null); // Break the relationship
    }

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPickupOrDeliver() {
		return pickupOrDeliver;
	}

	public void setPickupOrDeliver(String pickupOrDeliver) {
		this.pickupOrDeliver = pickupOrDeliver;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
    
    
}