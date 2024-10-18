package com.example.hoteltest.service;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hoteltest.dto.OrderEntityDTO;
import com.example.hoteltest.dto.OrderItemDTO;
import com.example.hoteltest.dto.ProductDTO;
import com.example.hoteltest.dto.Response;
import com.example.hoteltest.dto.StoreResponseDTO;
import com.example.hoteltest.exceptiom.MyCustomException;
import com.example.hoteltest.model.Cart;
import com.example.hoteltest.model.CartItem;
import com.example.hoteltest.model.OrderEntity;
import com.example.hoteltest.model.OrderItem;
import com.example.hoteltest.model.Product;
import com.example.hoteltest.model.Store;
import com.example.hoteltest.model.User;
import com.example.hoteltest.repository.CartRepository;
import com.example.hoteltest.repository.OrderRepository;
import com.example.hoteltest.repository.ProductRepository;
import com.example.hoteltest.repository.StoreRepository;
import com.example.hoteltest.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

	@Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private StoreRepository storeRepository;
    
    
    
    
    
    @Transactional
    public OrderEntityDTO placeOrder(Long userId, String paymentMethod, String pickupOrDeliver) {
    	
    	try {
    		User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Fetch the cart for the user
            Cart cart = cartRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Cart not found"));

            if (cart.getCartItems().isEmpty()) {
                throw new RuntimeException("Cart is empty");
            }

            // Create new OrderEntity and set basic information
            OrderEntity order = new OrderEntity();
            order.setBuyer(user);
            order.setCreatedAt(LocalDateTime.now());
            order.setStatus("PENDING");


            // Initialize the list of OrderItems in the service
            List<OrderItem> orderItems = new ArrayList<>();
            BigDecimal totalAmount = BigDecimal.ZERO;
            
            boolean gcashAvailable = true; 
            boolean deliveryAvailable = true; 

            // Convert cart items to order items
            for (CartItem cartItem : cart.getCartItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(cartItem.getProduct().getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
                
                // Set the relationship manually without needing addOrderItem
                orderItem.setOrder(order);

                // Add orderItem to the list of OrderItems
                orderItems.add(orderItem);

                // Calculate total amount
                totalAmount = totalAmount.add(orderItem.getPrice());
                
                if (!cartItem.getProduct().getStore().isGcash()) {
                	gcashAvailable = false;
                }
                if (!cartItem.getProduct().getStore().isDoDelivery()) {
                    deliveryAvailable = false;
                }
            }

            // Set the total price and order items in the order entity
            order.setTotalPrice(totalAmount);
            order.setOrderItems(orderItems); // Set the list of order items

            
            
            
            
         // Validate Payment Method (GCash or Cash)
            if (!gcashAvailable && paymentMethod.equals("gcash")) {
                throw new RuntimeException("One or more products do not support GCash payment");
            } else {
                // If GCash is not available, fall back to cash if user chose GCash
                order.setPaymentMethod(gcashAvailable ? paymentMethod : "cash");
            }

            // Validate Pickup or Delivery option
            if (!deliveryAvailable && pickupOrDeliver.equals("delivery")) {
                throw new RuntimeException("One or more products do not support delivery");
            } else {
                // If Delivery is not available, fall back to pickup if user chose delivery
                order.setPickupOrDeliver(deliveryAvailable ? pickupOrDeliver : "pickup");
            }
            
            
     
            // Save the order to the repository
            orderRepository.save(order);

            // Optionally clear the cart after placing the order
            cart.getCartItems().clear();
            cartRepository.save(cart);

            return new OrderEntityDTO(order);
    	} catch (Exception e) {
    	    System.out.println("Error saving order: " + e.getMessage());
    	    e.printStackTrace();
    	    throw new RuntimeException("Could not save order.");
    	}
    	
        
    }



    public List<OrderEntityDTO> getOrdersForSeller(Long storeId) {
    	 //TODO show full buyer info
        List<OrderEntity> orders = orderRepository.findOrdersByStoreId(storeId);
        return orders.stream()
                     .map(OrderEntityDTO::new)  // we must have that special constructor
                     .collect(Collectors.toList());
    }

    
    
    //test if new update did not wor:
    	//use entity instaed of DTO
    
    
    public Response getOrdersOfSpecificUser(Long userId) {
    	
    	Response response = new Response();
        try {
        	User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        	System.out.println("User: " + user.getId() + ", Email: " + user.getEmail());

            List<OrderEntity> orders = orderRepository.findByBuyer(user);

            // Log the fetched orders
            System.out.println("IF CALLED, USE FORLOOP THEN MANUALL DO THIS Fetched Orders: " + orders.size());

            for (OrderEntity order : orders) {
                System.out.println("Order: " + order.getId() + ", Total Items: " + order.getOrderItems().size());

                // Check each order item
                for (OrderItem item : order.getOrderItems()) {
                    System.out.println("OrderItem: " + item.getId() + ", Product: " + item.getProduct().getName() 
                        + ", Quantity: " + item.getQuantity() + ", Price: " + item.getPrice());
                }
            }

            // Map orders to DTOs
            List<OrderEntityDTO> ordersDto = orders.stream()
                    .map(OrderEntityDTO::new)
                    .collect(Collectors.toList());

            // Log the DTOs
            for (OrderEntityDTO orderDTO : ordersDto) {
                System.out.println("OrderDTO: " + orderDTO.getOrderId() + ", Items: " + orderDTO.getItems());
            }
            response.setListOfOrderEntityDTO(ordersDto);
            response.setStatusCode(200);
            Store store = orders.get(0).getOrderItems().get(0).getProduct().getStore();
            response.setStoreBlock(store.getBlock());
            response.setStoreId(store.getId());
            response.setStoreLot(store.getLot());
            response.setStoreName(store.getName());
            response.setPaymentMethod(orders.get(0).getPaymentMethod());
            response.setPickupOrDeliver(orders.get(0).getPickupOrDeliver());
//            response.setStorePhoneNumber(store.getPhoneNumber());
          
            return response;
            
        } catch (Exception e) {
            response.setStatusCode(404);
            response.setMessage("Product not found");
        }
        return response;
    }
    
    	
    



    
    public OrderEntityDTO getSpecificOrderDetails(Long orderId, Long storeId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Ensure the seller is authorized to view this order
        boolean sellerHasProduct = order.getOrderItems().stream()
                .anyMatch(orderItem -> orderItem.getProduct().getStore().getId().equals(storeId));
        
        if (!sellerHasProduct) {
            throw new RuntimeException("You are not authorized to view this order");
        }

        return new OrderEntityDTO(order);  // Return the order details
    }
    
    @Transactional
    public OrderEntityDTO updateOrderStatus(Long orderId, String status, Long sellerId) {
    	//get orders by orderid
    	OrderEntity order = orderRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

    	
    	boolean sellerIsTheOwnerOfProduct = order.getOrderItems().stream()
    			.anyMatch(item -> item.getProduct().getStore().getId().equals(sellerId));
    	
    	if (!sellerIsTheOwnerOfProduct) {
    		throw new MyCustomException("Not authorized to edit");
    	}
    	
    	order.setStatus(status);
    	orderRepository.save(order);
    	return new OrderEntityDTO(order);
    	//verify if current user is allowed to change the status thru sellerId
    	
    	//order.setStatus
    }
    
    @Transactional
    public OrderEntityDTO confirmOrder(User user, Long orderId) {
    	//first find the order id
    	
    	// loop thru items to 
    	//		verify if user/seller is authorized to confirm
    	//		check stock availability
    	//		decrementing the stock
    	
    	//	setproductQuantity = product.getQuantity - order.getQuantity
    	//	save
    	
    	
    	
    	//update seller revenue
    	
    	OrderEntity order = orderRepository.findById(orderId)
    			.orElseThrow(() -> new RuntimeException("product not found"));
    	
    	//reduce the product quantity thru Buyer's order -> List of  orderItem
    	for(OrderItem orderItem: order.getOrderItems()) { //listOFOrderItemjs
    		Product product = orderItem.getProduct();
    		
    		if (!product.getStore().getId().equals(user.getStore().getId())) {
                throw new RuntimeException("Unauthorized access");
    		}
    		
    		if (product.getQuantity() < orderItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
    		}
    		
    		//decremenet the stock value
    		product.setQuantity(product.getQuantity() - orderItem.getQuantity());
    		productRepository.save(product);
    	}
    	
    	//update seller salary
    	Store store = storeRepository.findById(user.getStore().getId())
    			.orElseThrow(() -> new RuntimeException("store not found"));
    	
    	
    	//seller.setRevenue
    	
    	order.setStatus("ACCEPTED");
    	OrderEntity savedOrder =  orderRepository.save(order);
    	return new OrderEntityDTO(savedOrder);
    	

    }
    
    @Transactional
    public OrderEntityDTO rejectOrderOfUser(User user, Long orderId) {
    	//find the order thru id
    	
    	//use for loop thru orderitem
    	//	verify if seller can edit
    	//
    	//orders.setStatus("CANCELLED")
    	
    	OrderEntity order = orderRepository.findById(orderId)
    			.orElseThrow(() -> new RuntimeException("seller not found"));

    	for(OrderItem orderItem: order.getOrderItems()) {
    		Product product = orderItem.getProduct();
    		
    		if(!product.getStore().getId().equals(user.getStore().getId())) {
                throw new RuntimeException("Unauthorized access");
    		}
    		
    	}
    	
    	order.setStatus("CANCELLED");
    	OrderEntity savedOrder = orderRepository.save(order);
    	return new OrderEntityDTO(savedOrder);
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
