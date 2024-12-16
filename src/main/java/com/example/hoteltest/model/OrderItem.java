package com.example.hoteltest.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


//specifric whati  ordered.. similar to CartItem.java class
@Entity
public class OrderItem {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;  //get the products from cartItem.. find by user
    //loop thru the cart.getItems.stream.map(item -> create orderItem and set all info )
    //	inside the loop.

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private OrderEntity order; // Order reference

    private BigDecimal price;
    
    
    //NEW DELETE IF ERROR.. THIS ARE FOR DISCOUNTS or changes of order price. FOR FUTURE UPDATES ONLY
		//    @Column(nullable = true)
		//    private BigDecimal priceAtPurchase;
		//    @Column(nullable = true)
		//    private BigDecimal costAtPurchase;
		//
		//    @Column(nullable = true)
		//    private BigDecimal discount; // Total discount applied to the order.. not PER ITEM. meaning buo na. maybe for promo or such

    private int quantity;

    // Default constructor
    public OrderItem() {}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public OrderEntity getOrder() {
		return order;
	}

	public void setOrder(OrderEntity order) {
		this.order = order;
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


//	public BigDecimal getPriceAtPurchase() {
//		return priceAtPurchase;
//	}
//
//
//	public void setPriceAtPurchase(BigDecimal priceAtPurchase) {
//		this.priceAtPurchase = priceAtPurchase;
//	}
//
//
//	public BigDecimal getCostAtPurchase() {
//		return costAtPurchase;
//	}
//
//
//	public void setCostAtPurchase(BigDecimal costAtPurchase) {
//		this.costAtPurchase = costAtPurchase;
//	}
//
//
//	public BigDecimal getDiscount() {
//		return discount;
//	}
//
//
//	public void setDiscount(BigDecimal discount) {
//		this.discount = discount;
//	}
//

	

    // Constructors, getters, and setters
    
    
}