package com.example.hoteltest.controller;

import java.util.List;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.Map;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.example.hoteltest.service.OrderUpdateEvent;
import com.example.hoteltest.service.sellerdashboard.RankingCustomerDTO;
import com.example.hoteltest.service.sellerdashboard.RecentOrdersTable;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    private final List<Consumer<OrderEntityDTO>> orderSubscribers = new CopyOnWriteArrayList<>();

    
    //Where we get the pushed events from @EventListener
    @GetMapping(value = "/updates", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<OrderEntityDTO> getOrderUpdates() {
        return Flux.create(sink -> {
            Consumer<OrderEntityDTO> consumer = sink::next;
            orderSubscribers.add(consumer); //register new subscribers
            sink.onCancel(() -> orderSubscribers.remove(consumer));
        });
    }
    
    
    // Event listener that pushes order updates to all connected clients
    @EventListener
    public void handleOrderUpdateEvent(OrderUpdateEvent event) {
        orderSubscribers.forEach(consumer -> consumer.accept(event.getOrderDto()));
    }

    
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
    	OrderEntityDTO orderDTO = orderService.placeOrder(currentUser.getId(), paymentMethod, pickupOrDeliver, currentUser.getFullName());
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
		OrderEntityDTO updatedOrder = orderService.confirmOrder(currentUser, orderId, message, currentUser.getFullName());
		return ResponseEntity.ok(updatedOrder);
	}
    
    @PutMapping("seller/orders/{storeId}/reject/{orderId}")
    public ResponseEntity<OrderEntityDTO> rejectOrderStatus(@PathVariable Long storeId,
    		@PathVariable Long orderId, @RequestBody Map<String, Object> payload) {
		
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User currentUser = (User) authentication.getPrincipal();
	    
	    String message = payload.get("sellerMessage").toString();
		OrderEntityDTO updatedOrder = orderService.rejectOrderOfUser(currentUser, orderId, message, currentUser.getFullName());
		return ResponseEntity.ok(updatedOrder);
	}
    
    @GetMapping("api/tableorders")
    public List<RecentOrdersTable> findRecentProductOrdersByStoreUserIdForTableUsingDb(){
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User currentUser = (User) authentication.getPrincipal();
    	return orderService.findRecentProductOrdersByStoreUserIdForTableUsingDb(currentUser.getId());
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    //CUSTOMER RANKING
    
    @GetMapping("customers/ranking-by/order-count/{size}")
    public List<RankingCustomerDTO> rankCustomersByOrderCount(
    		@AuthenticationPrincipal User user,
    		@PathVariable int size) {
    	
    	return orderService.rankCustomersByOrderCount(user.getStore().getId(), size);
    }
    
    @GetMapping("customers/ranking-by/order-weightedscore/{size}")
    public List<RankingCustomerDTO> rankCustomersByWeightedScore(
    		@AuthenticationPrincipal User user,
    		@PathVariable int size) {
    	
    	return orderService.rankCustomersByWeightedScore(user.getStore().getId(), size);
    }
    
    @GetMapping("customers/ranking-by/order-profit/{size}")
    public List<RankingCustomerDTO> rankCustomersByProfit(
    		@AuthenticationPrincipal User user,
    		@PathVariable int size) {
    	
    	return orderService.rankCustomersByProfit(user.getStore().getId(), size);
    }
    
    @GetMapping("customers/ranking-by/order-receny/{size}")
    public List<RankingCustomerDTO> rankCustomersByRecencyActive(
    		@AuthenticationPrincipal User user,
    		@PathVariable int size) {
    	
    	return orderService.rankCustomersByRecencyActive(user.getStore().getId(), size);
    }
    
    @GetMapping("customers/ranking-by/order-revenue/{size}")
    public List<RankingCustomerDTO> rankCustomersByRevenueGive(
    		@AuthenticationPrincipal User user,
    		@PathVariable int size) {
    	
    	return orderService.rankCustomersByRevenueGive(user.getStore().getId(), size);
    }
    
}