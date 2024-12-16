package com.example.hoteltest.service.sellerdashboard;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class RecentOrdersTable {
	private Long orderId; //from orderEntity
    private String status; //from orderEntity
    private LocalDateTime createdAt; //from orderEntity
    private BigDecimal totalPrice; //from orderEntity
    private String productName; //from orderItem
    
    
    
    
	public RecentOrdersTable(String productName, Long orderId, LocalDateTime createdAt, String status, BigDecimal totalPrice
			) {
		super();
		this.orderId = orderId;
		this.status = status;
		this.createdAt = createdAt;
		this.totalPrice = totalPrice;
		this.productName = productName;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}

   
    
}
