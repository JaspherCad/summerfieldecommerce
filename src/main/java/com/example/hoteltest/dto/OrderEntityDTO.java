package com.example.hoteltest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.example.hoteltest.model.OrderEntity;
import com.example.hoteltest.model.OrderItem;
import com.example.hoteltest.model.Store;
import com.example.hoteltest.model.User;


public class OrderEntityDTO {
	private Long orderId;
    private Long userId;
    private List<OrderItemDTO> items;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;
    private UserDTO buyerDto; //implement in constructor to set the UserDTO using its constructor
    private String status; // PENDING, SHIPPED, DELIVERED
    private Long storeId; // Store ID
    private String storeName; // Store Name
    private boolean gcash; // Example field from Store
    private boolean doDelivery; // Example field from Store
    private String paymentMethod; //gcash or cash
    private String pickupOrDeliver; //pickup or delivery
    private String messageFromSeller;
    // Constructors, getters, setters

    public OrderEntityDTO() {}

    public OrderEntityDTO(OrderEntity order) {
    	this.paymentMethod = order.getPaymentMethod();
    	this.pickupOrDeliver = order.getPickupOrDeliver();
        this.orderId = order.getId();
        this.userId = order.getBuyer().getId();
        this.totalAmount = order.getTotalPrice();
        this.orderDate = order.getCreatedAt();
        if (order.getOrderItems() != null) {
            this.items = order.getOrderItems().stream()
                              .map(OrderItemDTO::new)
                              .collect(Collectors.toList());
        } else {
            this.items = Collections.emptyList(); // Return an empty list if there are no items
        }
        
        if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
            Store store = order.getOrderItems().get(0).getProduct().getStore();
            if (store != null) {
                this.storeId = store.getId();
                this.storeName = store.getName();
                this.gcash = store.isGcash();
                this.doDelivery = store.isDoDelivery();
            }
        }

        this.buyerDto = new UserDTO(order.getBuyer());
        this.status = order.getStatus();

        // Log the order details for debugging
        System.out.println("OrderDTO: " + this.orderId + ", Items: " + this.items);
    }

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<OrderItemDTO> getItems() {
		return items;
	}

	public void setItems(List<OrderItemDTO> items) {
		this.items = items;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public UserDTO getBuyerDto() {
		return buyerDto;
	}

	public void setBuyerDto(UserDTO buyerDto) {
		this.buyerDto = buyerDto;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
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

	public String getMessageFromSeller() {
		return messageFromSeller;
	}

	public void setMessageFromSeller(String messageFromSeller) {
		this.messageFromSeller = messageFromSeller;
	}

	
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}