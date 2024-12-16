package com.example.hoteltest.service.sellerdashboard;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.example.hoteltest.dto.OrderItemDTO;
import com.example.hoteltest.model.OrderEntity;

public class OrderSummary {
	private Long orderId;
    private LocalDateTime orderDate;
    private BigDecimal orderTotal;
    private OrderEntity orderEntity;
    private String orderStatus;	
    private List<OrderItemDetailsDTO> items;
    
    
	public OrderEntity getOrderEntity() {
		return orderEntity;
	}
	public void setOrderEntity(OrderEntity orderEntity) {
		this.orderEntity = orderEntity;
	}
	public OrderSummary(OrderEntity order) {
		super();
		this.orderId = order.getId();
		this.orderDate = order.getCreatedAt();
		this.orderTotal = order.getTotalPrice();
	}
	
	
	public OrderSummary(Long orderId, LocalDateTime orderDate, String orderStatus, BigDecimal totalPrice, List<OrderItemDetailsDTO> items) {
		this.orderId = orderId; 
		this.orderDate = orderDate; 
		this.orderStatus = orderStatus; 
		this.orderTotal = totalPrice; 
		this.items = items; 
		}
	
	public OrderSummary(Long orderId, LocalDateTime orderDate, BigDecimal orderTotal) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.orderTotal = orderTotal;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	
	public List<OrderItemDetailsDTO> getItems() {
		return items;
	}
	public void setItems(List<OrderItemDetailsDTO> items) {
		this.items = items;
	}
	public LocalDateTime getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}
	public BigDecimal getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(BigDecimal orderTotal) {
		this.orderTotal = orderTotal;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public static class OrderItemDetailsDTO {
		private String productName;
		private int quantity;
		private BigDecimal price;
		
		
		
		
		
		public OrderItemDetailsDTO(String productName, int quantity, BigDecimal price) {
			super();
			this.productName = productName;
			this.quantity = quantity;
			this.price = price;
		}
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		public BigDecimal getPrice() {
			return price;
		}
		public void setPrice(BigDecimal price) {
			this.price = price;
		}
		
		
	}
    
    

}
