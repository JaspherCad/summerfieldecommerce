package com.example.hoteltest.dto;

import java.util.List;

public class OrderRequest {
	private Long userId;
    private List<OrderItemDTO> items;
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
	public OrderRequest() {
		super();
	}
    
    
    
}
