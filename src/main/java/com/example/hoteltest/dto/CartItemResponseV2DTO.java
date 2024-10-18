package com.example.hoteltest.dto;

import com.example.hoteltest.model.Product;

public class CartItemResponseV2DTO {
	private Long id;
    private int quantity;
    private ProductResponseDTO productResponseDto;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public ProductResponseDTO getProductResponseDto() {
		return productResponseDto;
	}
	public void setProductResponseDto(ProductResponseDTO productResponseDto) {
		this.productResponseDto = productResponseDto;
	}
	


    
}
