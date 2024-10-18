package com.example.hoteltest.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hoteltest.dto.Response;
import com.example.hoteltest.model.Cart;
import com.example.hoteltest.model.CartItem;
import com.example.hoteltest.model.User;
import com.example.hoteltest.repository.CartRepository;
import com.example.hoteltest.service.CartService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	CartService cartService;
	@Autowired
	CartRepository cartRepository;
	
	
	
	@PostMapping("/add")
	ResponseEntity<Response> addToCart(@RequestBody Map<String, Object> payload){
		Long productId = Long.parseLong(payload.get("productId").toString());
	    Integer quantity = Integer.parseInt(payload.get("quantity").toString());
	    
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User currentUser = (User) authentication.getPrincipal();
	    
		Response response = cartService.addToCart(productId, quantity, currentUser.getEmail());
        return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
//	@GetMapping
//	public ResponseEntity<Response> getCartOfCurrentUser() {
//	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//	    User currentUser = (User) authentication.getPrincipal();
//	    
//	    Cart cart = cartRepository.findByUser(currentUser)
//	                .orElseThrow(() -> new RuntimeException("Cart not found"));
//	    
//	    // Debugging log to print cart items
//	    System.out.println("Cart ID: " + cart.getId());
//	    System.out.println("Number of Cart Items: " + cart.getCartItems().size());
//	    for (CartItem cartItem : cart.getCartItems()) {
//	        System.out.println("Product ID: " + cartItem.getProduct().getId() + ", Quantity: " + cartItem.getQuantity());
//	    }
//
//	    // Prepare the response
//	    Response response = new Response();
//	    response.setStatusCode(200);
//	    response.setMessage("Cart retrieved successfully");
//	    response.setCart(cart);
//
//	    return ResponseEntity.ok(response);
//	}
	@GetMapping
	ResponseEntity<Response> getCartOfCurrentUserv2(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User currentUser = (User) authentication.getPrincipal();
	    
	    Response response = cartService.getCart(currentUser);

	 

	    Cart cart = cartRepository.findByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    
		    // Debugging log to print cart items
		    System.out.println("Cart ID: " + cart.getId());
		    System.out.println("Number of Cart Items: " + cart.getCartItems().size());
		    for (CartItem cartItem : cart.getCartItems()) {
		        System.out.println("Product ID: " + cartItem.getProduct().getId() + ", Quantity: " + cartItem.getQuantity());
		    }
	    
        return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@Transactional
	@PutMapping("/clear")
	public Response clearAllCartItem() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User currentUser = (User) authentication.getPrincipal();
	    Response response = cartService.clearCart(currentUser);
	    return response;
	    
	

	    
	    

	    
	}
	
	@Transactional
	@PutMapping("/updateCart")
	public Response updateProduct(@RequestBody Map<String, Object> payload) {
		
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User currentUser = (User) authentication.getPrincipal();
	    
	    
	    Long productId = Long.parseLong(payload.get("productId").toString());
	    Integer quantity = Integer.parseInt(payload.get("quantity").toString());
	    
	    Response response = cartService.updateCart(productId, quantity, currentUser);
	    return response;
	    
	

	    
	    

	    
	}
//	@GetMapping("/v3")
//	public ResponseEntity<Cart> getCartOfCurrentUserv3() {
//	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//	    User currentUser = (User) authentication.getPrincipal();
//	    
//	    Cart cart = cartRepository.findByUser(currentUser)
//	                .orElseThrow(() -> new RuntimeException("Cart not found"));
//	    
//	    return ResponseEntity.ok(cart);
//	}
	
	@Transactional
	@DeleteMapping("/item/{cartItemId}")
	public ResponseEntity<Response> deleteCartItem(@PathVariable Long cartItemId) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User currentUser = (User) authentication.getPrincipal();

	    Response response = cartService.deleteFromCart(cartItemId, currentUser);

	    return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
}
