package com.example.hoteltest.service.sellerdashboard;

import java.math.BigDecimal;
import java.util.List;

public class SalesOverviewDTO {
    private BigDecimal totalSales;
    private List<ProductSummary> topProducts;
    private List<OrderSummary> recentOrders;
	public BigDecimal getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}
	public List<ProductSummary> getTopProducts() {
		return topProducts;
	}
	public void setTopProducts(List<ProductSummary> topProducts) {
		this.topProducts = topProducts;
	}
	public List<OrderSummary> getRecentOrders() {
		return recentOrders;
	}
	public void setRecentOrders(List<OrderSummary> recentOrders) {
		this.recentOrders = recentOrders;
	}

    
    
}
