package com.example.hoteltest.service.sellerdashboard;


public class TopProductDTO {
	Long productId;
	String productName;
	Long totalOrdersQuantity;
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
	
	
	
	public TopProductDTO(Long productId, String productName, Long totalOrdersQuantity) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.totalOrdersQuantity = totalOrdersQuantity;
	}
	@Override
	public String toString() {
		return "TopProductDTO [productId=" + productId + ", productName=" + productName + ", totalOrdersQuantity="
				+ totalOrdersQuantity + "]";
	}
	public Long getTotalOrdersQuantity() {
		return totalOrdersQuantity;
	}
	public void setTotalOrdersQuantity(Long totalOrdersQuantity) {
		this.totalOrdersQuantity = totalOrdersQuantity;
	}
	
	
	
}
