package com.example.hoteltest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hoteltest.dto.OrderEntityDTO;
import com.example.hoteltest.dto.OrderRequest;
import com.example.hoteltest.dto.Response;
import com.example.hoteltest.model.User;
import com.example.hoteltest.service.OrderService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    
    @GetMapping("buyer/me")//since seller can be a buyer too, use UserId
    public Response getOrdersOfSpecificUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        Response response  = orderService.getOrdersOfSpecificUser(currentUser.getId());
        return response;
    }
    
    
    @PostMapping("/place")
    public ResponseEntity<OrderEntityDTO> placeOrder(@RequestBody Map<String, Object> payload) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User currentUser = (User) authentication.getPrincipal();
	    
	    
	    String paymentMethod = payload.get("paymentMethod").toString();
	    String pickupOrDeliver = payload.get("pickupOrDeliver").toString();
    	OrderEntityDTO orderDTO = orderService.placeOrder(currentUser.getId(), paymentMethod, pickupOrDeliver);
        return ResponseEntity.ok(orderDTO);
    }
    
    @GetMapping("seller/orders")
    public ResponseEntity<List<OrderEntityDTO>> getOrdersForSeller() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Long sellerId = currentUser.getId();  // Get the authenticated seller's ID

        List<OrderEntityDTO> orders = orderService.getOrdersForSeller(sellerId);
        return ResponseEntity.ok(orders);
    }
    
//    
//    
    @GetMapping("seller/orders/{orderId}")
    public ResponseEntity<OrderEntityDTO> getSpecificOrderDetails(@PathVariable Long orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Long sellerId = currentUser.getId();  // Get the authenticated seller's ID

        OrderEntityDTO orderDetails = orderService.getSpecificOrderDetails(orderId, sellerId);
        return ResponseEntity.ok(orderDetails);
    }
//    @PutMapping("seller/orders/{orderId}/status")
//    public ResponseEntity<OrderEntityDTO> updateOrderStatus(@PathVariable Long orderId,
//                                                            @RequestParam String newStatus) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = (User) authentication.getPrincipal();
//        Long sellerId = currentUser.getId();  // Get the authenticated seller's ID
//
//        OrderEntityDTO updatedOrder = orderService.updateOrderStatus(orderId, newStatus, sellerId);
//        return ResponseEntity.ok(updatedOrder);
//    }
//    
    
    
    
    
    
    //confirmOrder(Long orderId, Long storeId)
    @PutMapping("seller/orders/{storeId}/accept/{orderId}")
    public ResponseEntity<OrderEntityDTO> confirmOrderStatus(@PathVariable Long storeId,
    		@PathVariable Long orderId, @RequestBody Map<String, Object> payload) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User currentUser = (User) authentication.getPrincipal();
		
	    String message = payload.get("sellerMessage").toString();
		OrderEntityDTO updatedOrder = orderService.confirmOrder(currentUser, orderId, message);
		return ResponseEntity.ok(updatedOrder);
	}
    
    @PutMapping("seller/orders/{storeId}/reject/{orderId}")
    public ResponseEntity<OrderEntityDTO> rejectOrderStatus(@PathVariable Long storeId,
    		@PathVariable Long orderId, @RequestBody Map<String, Object> payload) {
		
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User currentUser = (User) authentication.getPrincipal();
	    String message = payload.get("sellerMessage").toString();
		OrderEntityDTO updatedOrder = orderService.rejectOrderOfUser(currentUser, storeId, message);
		return ResponseEntity.ok(updatedOrder);
	}
    
    
}