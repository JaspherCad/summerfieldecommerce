package com.example.hoteltest.dto;

import java.util.List;

public class HomePageProductDTO {
    private List<ProductDTO> featuredProducts;
    private List<ProductDTO> newArrivals;
    private List<ProductDTO> bestSellers;
    
    
    
    
    
    
    
	public List<ProductDTO> getFeaturedProducts() {
		return featuredProducts;
	}
	public void setFeaturedProducts(List<ProductDTO> featuredProducts) {
		this.featuredProducts = featuredProducts;
	}
	public List<ProductDTO> getNewArrivals() {
		return newArrivals;
	}
	public void setNewArrivals(List<ProductDTO> newArrivals) {
		this.newArrivals = newArrivals;
	}
	public List<ProductDTO> getBestSellers() {
		return bestSellers;
	}
	public void setBestSellers(List<ProductDTO> bestSellers) {
		this.bestSellers = bestSellers;
	}
	public HomePageProductDTO(List<ProductDTO> featuredProducts, List<ProductDTO> newArrivals,
			List<ProductDTO> bestSellers) {
		super();
		this.featuredProducts = featuredProducts;
		this.newArrivals = newArrivals;
		this.bestSellers = bestSellers;
	}

    
    
    
    // Constructor, getters, and setters
    
    
}
