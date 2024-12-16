package com.example.hoteltest.service;

import java.math.BigDecimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.example.hoteltest.model.UserLog;
import com.example.hoteltest.repository.CartRepository;
import com.example.hoteltest.repository.OrderRepository;
import com.example.hoteltest.repository.ProductRepository;
import com.example.hoteltest.repository.StoreRepository;
import com.example.hoteltest.repository.UserLogRepository;
import com.example.hoteltest.repository.UserRepository;
import com.example.hoteltest.service.sellerdashboard.RankingCustomerDTO;
import com.example.hoteltest.service.sellerdashboard.RecentOrdersTable;
import com.example.hoteltest.service.sellerdashboard.TopProductDTO;

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

    @Autowired
    private UserLogRepository userLogRepository;
    
    
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public OrderService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
    
    
    
    @Transactional
    public OrderEntityDTO placeOrder(Long userId, String paymentMethod, String pickupOrDeliver, String currentUser) {
    	
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
            
            // Log for tracking each seller's sales
            List<UserLog> sellerLogs = new ArrayList<>();
            BigDecimal totalCost = BigDecimal.ZERO;
            Store store = null; // Initialize store variable

            // Convert cart items to order items
            for (CartItem cartItem : cart.getCartItems()) {
            	
                OrderItem orderItem = new OrderItem();
                
                //FROM CART...
                
                BigDecimal cost = cartItem.getProduct().getCost(); 
                if (cost == null) { 
                	cost = cartItem.getProduct().getPrice(); 
                }
                
                totalCost = totalCost.add(cost.multiply(new BigDecimal(cartItem.getQuantity())));
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(cartItem.getProduct().getPriceAfterDiscount().multiply(new BigDecimal(cartItem.getQuantity())));
                
                //set bidirectional then;
                orderItem.setOrder(order);

                // add orderItem to the list of OrderItems
                orderItems.add(orderItem);
                

                totalAmount = totalAmount.add(orderItem.getPrice());
                
                if (!cartItem.getProduct().getStore().isGcash()) {
                	gcashAvailable = false;
                }
                if (!cartItem.getProduct().getStore().isDoDelivery()) {
                    deliveryAvailable = false;
                }
                
             // the store variable to the product's store
                if (store == null) {
                    store = cartItem.getProduct().getStore();
                }
                
                //log SELLER action: SA BABA BUYER
                User seller = cartItem.getProduct().getStore().getUser();

                String sellerDescription = " purchased " + cartItem.getQuantity() + " of " + cartItem.getProduct().getName();
                UserLog sellerLog = new UserLog(seller, "SALE", sellerDescription, currentUser);
                sellerLogs.add(sellerLog); //later, use for loop to retrieve all into one UserLog.repository
            }

            // Set the total price and order items in the order entity
            order.setTotalPrice(totalAmount);
            order.setOrderItems(orderItems); // Set the list of order items

            
            
            order.setCost(totalCost);
            if (store != null) {
                order.setStore(store);
            } else {
                throw new RuntimeException("Store info error");
            }
            
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
            OrderEntity orderSaved =  orderRepository.save(order);

            // Optionally clear the cart after placing the order
            cart.getCartItems().clear();
            cartRepository.save(cart);
            
            
            //log buyer action (user)
            String description = " purchased items with orderId: " + orderSaved.getId();
            UserLog log = new UserLog(user, "ORDER", description ,currentUser);
            userLogRepository.save(log);
            
            //i want to loop thru.... 
            for(UserLog sellerLog: sellerLogs) {
            	userLogRepository.save(sellerLog);
            }
            
            
            


            return new OrderEntityDTO(order);
    	} catch (Exception e) {
    	    System.out.println("Error saving order: " + e.getMessage());
    	    e.printStackTrace();
    	    throw new RuntimeException("Could not save order.");
    	}
    	
        
    }



    public List<OrderEntityDTO> getOrdersForSeller(Long sellerId) {
    	 //TODO show full buyer info
    	User user = userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    	
    	Store store = storeRepository.findById(user.getStore().getId())
        .orElseThrow(() -> new RuntimeException("User not found"));

        List<OrderEntity> orders = orderRepository.findOrdersByStoreId(store.getId());
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
    
    	
    



    //error will be here. USER ID IS NOT ALWAYS STORE ID
    
    public OrderEntityDTO getSpecificOrderDetails(Long orderId, Long sellerId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        User user = userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
//        Long storeId = user.getStore().getId();


		Store store = storeRepository.findById(user.getStore().getId())
				 .orElseThrow(() -> new RuntimeException("store not found"));
        
        // Ensure the seller is authorized to view this order
        boolean sellerHasProduct = order.getOrderItems().stream()
                .anyMatch(orderItem -> orderItem.getProduct().getStore().getId().equals(store.getId()));
        
        if (!sellerHasProduct) {
            throw new RuntimeException("You are not authorized to view this order");
        }

        return new OrderEntityDTO(order);  // Return the order details
    }
    
    @Transactional
    public OrderEntityDTO updateOrderStatus(Long orderId, String status, Long sellerId, String currentUser) {
    	//get orders by orderid
    	OrderEntity order = orderRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

    	User user = userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    	
    	Store store = storeRepository.findById(user.getStore().getId())
				 .orElseThrow(() -> new RuntimeException("store not found"));
       
        
//        Long storeId = user.getStore().getId();
    	
    	boolean sellerIsTheOwnerOfProduct = order.getOrderItems().stream()
    			.anyMatch(item -> item.getProduct().getStore().getId().equals(store.getId()));
    	
    	if (!sellerIsTheOwnerOfProduct) {
    		throw new MyCustomException("Not authorized to edit");
    	}
    	
    	order.setStatus(status); // PENDING, ACCEPTED, REJECTED
    	orderRepository.save(order);
    	
    	String description = currentUser + " updated order with orderId: " + order.getId();
        UserLog log = new UserLog(user, "UPDATE", description, currentUser);
        userLogRepository.save(log);

    	return new OrderEntityDTO(order);
    	//verify if current user is allowed to change the status thru sellerId
    	
    	//order.setStatus
    }
    
    @Transactional
    public OrderEntityDTO confirmOrder(User user, Long orderId, String message, String currentUser) {
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
    		
    		
    		//save the modified Product using repository;
    		//after saving, we can send the UPDATED PROUTDTO to publisher SSE
    		Product savedProduct = productRepository.save(product);
    		ProductDTO productDto = new ProductDTO(savedProduct);
            eventPublisher.publishEvent(new ProductUpdateEvent(this, productDto));  // Publish the event
    	}
    	
    	//update seller salary
    	Store store = storeRepository.findById(user.getStore().getId())
    			.orElseThrow(() -> new RuntimeException("store not found"));
    	
    	
    	//TODO seller.setRevenue
    	//TODO seller.setRevenue
    	//TODO seller.setRevenue
    	//TODO seller.setRevenue

    	
    	order.setStatus("ACCEPTED");
   
    	OrderEntity savedOrder =  orderRepository.save(order);
    	OrderEntityDTO orderEntityDTO =  new OrderEntityDTO(savedOrder);
    	orderEntityDTO.setMessageFromSeller(message);
    	
    	
    	
    	String description = " CONFIREMD ORDER " + order.getId();
        UserLog log = new UserLog(user, "ACCEPT", description, currentUser);
        userLogRepository.save(log);
        
        eventPublisher.publishEvent(new OrderUpdateEvent(this, orderEntityDTO));  // Publish the event
        //ALSO THE PRODUCT DTO
    	return orderEntityDTO;
    	

    }
    
    @Transactional
    public OrderEntityDTO rejectOrderOfUser(User user, Long orderId, String message, String currentUser) {
    	//find the order thru id
    	
    	//use for loop thru orderitem
    	//	verify if seller can edit
    	//
    	//orders.setStatus("CANCELLED")
    	
    	OrderEntity order = orderRepository.findById(orderId)
    			.orElseThrow(() -> new RuntimeException("seller not found"));

    	Store store = storeRepository.findById(user.getStore().getId())
				 .orElseThrow(() -> new RuntimeException("store not found"));
    	
    	for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            if (!product.getStore().getId().equals(user.getStore().getId())) {
                throw new RuntimeException("Unauthorized access");
            }
        }

        // Ensure order status can only be set to CANCELLED if it’s in a valid state
        if (order.getStatus().equals("CANCELLED")) {
            throw new RuntimeException("Order is already cancelled");
        }

        // Update order status
        order.setStatus("CANCELLED");
        
    	OrderEntity savedOrder =  orderRepository.save(order);
    	OrderEntityDTO orderEntityDTO =  new OrderEntityDTO(savedOrder);
    	orderEntityDTO.setMessageFromSeller(message);
    	
    	String description = "SELLER: " + store.getUser().getFullName() + " REJECTED ORDER " + order.getId();
        UserLog log = new UserLog(user, "REJECT", description, currentUser);
        userLogRepository.save(log);
        
        eventPublisher.publishEvent(new OrderUpdateEvent(this, orderEntityDTO));  // Publish the event

    	
    	return orderEntityDTO;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //FOR TABLE AT REACT (    //but for performance wise, use db.)
    //product's name: product's order id: product's order created at, product's order status
//product > oi > o. so Use the order to start the loop.
    public List<RecentOrdersTable> findRecentProductOrdersByStoreUserIdForTable(Long userId) {

        List<OrderEntity> orders = orderRepository.findByStoreUserId(userId);

        List<RecentOrdersTable> recentOrders = new ArrayList<>();

        for (OrderEntity order : orders) {
            LocalDateTime createdAt = order.getCreatedAt();
            String status = order.getStatus();
            Long orderId = order.getId();
            BigDecimal totalPrice = order.getTotalPrice();

            //get all first then input to List<>.
            //loop oi for productNAmes
            for(OrderItem oi: order.getOrderItems()) {
            	String productName = oi.getProduct().getName();
            	
            	recentOrders.add(new RecentOrdersTable(
                        productName,
                        orderId, //potentially may repeat
                        createdAt, //potentially may repeat
                        status, //potentially may repeat
                        totalPrice //potentially may repeat
                    ));
                }
            }
        
        recentOrders.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        
        return recentOrders;
        
        

    }
    public List<RecentOrdersTable> findRecentProductOrdersByStoreUserIdForTableUsingDb(Long userId){
    	return orderRepository.findRecentProductOrdersByStoreUserIdForTable(userId);
    }
    
    
    
    public List<RankingCustomerDTO> rankCustomersByOrderCount(Long storeId, int limit){
        Pageable pageable = PageRequest.of(0, limit);
    	Page<RankingCustomerDTO> topCustomers = orderRepository.rankCustomersByOrderCount(storeId, pageable);
    	
    	return topCustomers.getContent();
    }
    
    public List<RankingCustomerDTO> rankCustomersByRevenueGive(Long storeId, int limit){
        Pageable pageable = PageRequest.of(0, limit);
    	Page<RankingCustomerDTO> topCustomers = orderRepository.rankCustomersByRevenueGive(storeId, pageable);
    	
    	return topCustomers.getContent();
    }
    
    public List<RankingCustomerDTO> rankCustomersByProfit(Long storeId, int limit){
        Pageable pageable = PageRequest.of(0, limit);
    	Page<RankingCustomerDTO> topCustomers = orderRepository.rankCustomersByProfit(storeId, pageable);
    	
    	return topCustomers.getContent();
    }
    
    
    public List<RankingCustomerDTO> rankCustomersByWeightedScore(Long storeId, int limit){
        Pageable pageable = PageRequest.of(0, limit);
    	Page<RankingCustomerDTO> topCustomers = orderRepository.rankCustomersByWeightedScore(storeId, pageable);
    	
    	return topCustomers.getContent();
    }
    
    public List<RankingCustomerDTO> rankCustomersByRecencyActive(Long storeId, int limit){
        Pageable pageable = PageRequest.of(0, limit);
    	Page<RankingCustomerDTO> topCustomers = orderRepository.rankCustomersByRecencyActive(storeId, pageable);
    	
    	return topCustomers.getContent();
    }
    
    
}
