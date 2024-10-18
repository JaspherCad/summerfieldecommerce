package com.example.hoteltest.dto;

import java.math.BigDecimal;

import com.example.hoteltest.model.OrderEntity;
import com.example.hoteltest.model.OrderItem;
import com.example.hoteltest.model.Product;



public class OrderItemDTO {
	private Long productId;
    private String productName;
    private int quantity;
    private BigDecimal price;
    private ProductDTO productDTO; //full info

    // Constructors, getters, setters

    public OrderItemDTO() {}

    public OrderItemDTO(OrderItem item) {
    	if (item.getProduct() == null) {
            throw new RuntimeException("Product is null for OrderItem ID: " + item.getId());
        }
        this.productId = item.getProduct().getId();
        this.productName = item.getProduct().getName();
        this.quantity = item.getQuantity();
        this.price = item.getPrice();
        this.productDTO = new ProductDTO(item.getProduct());
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

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
	
	
    
    


