package com.example.hoteltest.service.sellerdashboard;

public class ProductSummary {
	private Long productId;
    private String name;
    private Long quantitySold;
    
    
	public ProductSummary(Long productId, String name, Long quantitySold) {
		super();
		this.productId = productId;
		this.name = name;
		this.quantitySold = quantitySold;
	}
	
	
	public ProductSummary() {
	}


	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getQuantitySold() {
		return quantitySold;
	}
	public void setQuantitySold(Long quantitySold) {
		this.quantitySold = quantitySold;
	}
    
    
}
