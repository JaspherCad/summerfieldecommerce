package com.example.hoteltest.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hoteltest.dto.CartItemResponseV2DTO;
import com.example.hoteltest.dto.ProductResponseDTO;
import com.example.hoteltest.dto.Response;
import com.example.hoteltest.dto.StoreResponseDTO;
import com.example.hoteltest.exceptiom.MyCustomException;
import com.example.hoteltest.model.Cart;
import com.example.hoteltest.model.CartItem;
import com.example.hoteltest.model.Product;
import com.example.hoteltest.model.Store;
import com.example.hoteltest.model.Tag;
import com.example.hoteltest.model.User;
import com.example.hoteltest.repository.CartItemRepository;
import com.example.hoteltest.repository.CartRepository;
import com.example.hoteltest.repository.ProductRepository;
import com.example.hoteltest.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;
    
    @Autowired
    ProductRepository productRepository;
    
    @Autowired
    CartItemRepository cartItemRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    
    @Transactional
    public Response clearCart(User user) {
        Response response = new Response();

        try {
        	//get cart thru findByUSer
        	//cart.getItems.clear
        	//save the cart into repository.
            Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

            cart.getCartItems().clear();
    	if (cart.getCartItems().isEmpty()) {
    		System.out.println("cart items: " + cart.getCartItems());
    	}else {
    		System.out.println("not empty");

    	}
    	

        // Ensure the cart total price is reset to zero
        cart.setCartTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);
        cartRepository.flush();  // Ensure the cart changes are flushed to the database
        entityManager.refresh(cart);



        // Prepare the response
        response.setStatusCode(200);
        response.setMessage("Cart cleared successfully");
        response.setCart(cart);  // Return the empty cart

        // Set the list of cart items to an empty list in the response
        response.setListOfCartItemsDTO(new ArrayList<>());
        response.setStoreResponseDTO(null); // Optionally reset store information

        
        }catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error clearing cart: " + e.getMessage());
        }

        return response;
    }
    
    // Method to add a product to the cart, including store information
    public Response addToCart(Long productId, Integer quantity, String userEmail) {
        Response response = new Response();
        try {
            // Find the user
            User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

            // Find the product
            Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

            // Check product availability
            if (product.getQuantity() < quantity) {
                throw new RuntimeException("Not enough stock available for product: " + product.getName());
            }

            // Find or create the user's cart
            Cart cart = cartRepository.findByUser(user).orElse(new Cart(user));

            
            
            if(!cart.getCartItems().isEmpty()) {
            	Store currentCartStore  = cart.getCartItems().get(0).getProduct().getStore();
            	Store newProductStore = product.getStore();
            	
            	if(!currentCartStore.getId().equals(newProductStore.getId())) {
            		// Custom status code and message for store mismatch
            	    response.setStatusCode(300);  // Use 300 or any other custom code within 200-299 range
            	    response.setMessage("Store mismatch. Would you like to clear the cart?");
            	    return response;
                    //STOP HERE
            	}
            }
            
            
            
            
            
            
            
            // Check if the product already exists in the cart
            Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(product.getId()))
                .findFirst();

            if (existingCartItem.isPresent()) {
                // Update the quantity of the existing cart item
            	CartItem cartItem = existingCartItem.get();
                int currentCartQuantity = cartItem.getQuantity(); // Current quantity in cart
                int newQuantity = currentCartQuantity + quantity; // Total quantity after adding the new quantity
                
                // Debugging output for tracking values
                System.out.println("Current cart quantity: " + currentCartQuantity);
                System.out.println("New quantity to add: " + quantity);
                System.out.println("Total new quantity: " + newQuantity);
                System.out.println("Product stock available: " + product.getQuantity());
                
                // Check if new quantity exceeds the product's stock
                if (newQuantity > product.getQuantity()) {
                    response.setStatusCode(297);  // Custom status code for quantity exceeding stock
                    response.setMessage("You requested " + newQuantity + " units, but only " + product.getQuantity() + " are available for " + product.getName() + ". Please reduce the quantity.");
                    return response;
                }
                
                // Update the cart item with the new quantity
                cartItem.setQuantity(newQuantity);
            } else {
                // Create a new CartItem
                CartItem newCartItem = new CartItem();
                newCartItem.setCart(cart);
                newCartItem.setProduct(product);
                newCartItem.setQuantity(quantity);
                cart.getCartItems().add(newCartItem);
            }

            // Save the cart (cascading will save cart items as well)
            cartRepository.save(cart);

            // Prepare the response
            response.setStatusCode(200);
            response.setMessage("Product added to cart");
            response.setCart(cart);
            System.out.println("Cart Items: " + cart.getCartItems().size());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error adding product to cart: " + e.getMessage());
        }
        return response;
    }

    // Method to retrieve the cart with product and store information
    public Response getCart(User user) {
        Response response = new Response();

        try {
            // Fetch the cart by user
            Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

            BigDecimal totalAmount = BigDecimal.ZERO;
            List<CartItemResponseV2DTO> cartItemsDTOs = new ArrayList<>();
            
            StoreResponseDTO storeDTO = null; // Store information will be set here


            for (CartItem cartItem : cart.getCartItems()) {
                // Create a CartItemResponseV2DTO object
                CartItemResponseV2DTO cartItemDTO = new CartItemResponseV2DTO();
                cartItemDTO.setId(cartItem.getId());
                cartItemDTO.setQuantity(cartItem.getQuantity());

                // Create a ProductResponseDTO object
                ProductResponseDTO productDTO = new ProductResponseDTO();
                Product product = cartItem.getProduct();
                productDTO.setId(product.getId());
                productDTO.setName(product.getName());
                productDTO.setDescription(product.getDescription());
                productDTO.setPrice(product.getPrice());
                productDTO.setQuantity(product.getQuantity());
                productDTO.setCategory(product.getCategory());
                productDTO.setImgSrc(product.getImgSrc());
                productDTO.setTagNames(product.getTags().stream()
                        .map(Tag::getName)
                        .collect(Collectors.toList()));
                productDTO.setGcash(cartItem.getProduct().getStore().isGcash());
                productDTO.setDoDelivery(cartItem.getProduct().getStore().isDoDelivery());


                // Include store information in the ProductResponseDTO
		//                Store store = product.getStore(); // Get the store from the product
		//                StoreResponseDTO storeDTO = new StoreResponseDTO(store); // Use the StoreResponseDTO constructor
		//                productDTO.setStore(storeDTO); // Set the store in the product DTO

                // Log each product being processed
                System.out.println("Processing CartItem ID: " + cartItem.getId());
                System.out.println("Product in CartItem: " + product.getName());

                // Add product price to the total amount
                totalAmount = totalAmount.add(product.getPrice().multiply(new BigDecimal(cartItem.getQuantity())));

                // Set the productDTO into the cartItemDTO
                cartItemDTO.setProductResponseDto(productDTO);
                
                // Log the CartItemResponseV2DTO before adding
                System.out.println("CartItemDTO: " + cartItemDTO.getProductResponseDto().getName());

                cartItemsDTOs.add(cartItemDTO); // Add the cartItemDTO to the list
                
                
             // Set the store information from the first product's store
                    Store store = product.getStore(); // Get the store from the product
                    storeDTO = new StoreResponseDTO(store); // Use the StoreResponseDTO constructor
//if issue remove storeDto
            }
            
            cart.setCartTotalPrice(totalAmount);
            
            // Prepare the response
            response.setStatusCode(200);
            response.setMessage("Cart retrieved successfully");
            response.setCart(cart); 
            response.setGcashAvailableOnStore(cart.getCartItems().get(0).getProduct().getStore().isGcash());
            response.setDeliveryAvailableOnStore(cart.getCartItems().get(0).getProduct().getStore().isDoDelivery());

            response.setListOfCartItemsDTO(cartItemsDTOs);
            response.setStoreResponseDTO(storeDTO);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving cart: " + e.getMessage());
        }

        return response;
    }

    // Method to delete a product from the cart
    public Response deleteFromCart(Long cartItemId, User currentUser) {
        Response response = new Response();
        try {
            Cart cart = cartRepository.findByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

            // Find the CartItem by its ID
            CartItem deletedItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));

            if (!cart.getCartItems().contains(deletedItem)) {
                throw new RuntimeException("CartItem does not belong to the user's cart");
            }

            cart.getCartItems().remove(deletedItem);
            cartRepository.save(cart);
            cartItemRepository.delete(deletedItem);

            response.setStatusCode(200);
            response.setMessage(deletedItem.getProduct().getName() + " removed from cart");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting product from cart: " + e.getMessage());
        }
        return response;
    }
    
    
    //implement edit cart item
    
    public Response updateCart(Long productId, Integer quantity, User user) {
        Response response = new Response();
        
        try {
            // Fetch the user's cart
            Cart cart = cartRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Cart not found"));
            
            // Fetch the product
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            
            // Find the cart item for this product
            Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                    .filter(cartItem -> cartItem.getProduct().getId().equals(product.getId()))
                    .findFirst();
            
            if (existingCartItem.isPresent()) {
                CartItem cartItem = existingCartItem.get();
                
                // Handle quantity update
                if (quantity <= 0) {
                    // Remove the item from the cart if the quantity is zero or less
                    cart.getCartItems().remove(cartItem);
                    response.setMessage("Item removed from cart.");
                } else if (quantity > product.getQuantity()) {
                    // If the requested quantity is more than available stock
                    response.setStatusCode(297); // Custom code for quantity exceeding stock
                    response.setMessage("Requested quantity exceeds available stock for " + product.getName());
                    return response;
                } else {
                    // Update the quantity
                    cartItem.setQuantity(quantity);
                    response.setMessage("Cart updated successfully.");
                }
            } else {
                response.setStatusCode(404); // Custom code for item not found
                response.setMessage("Product not found in cart.");
                return response;
            }
            
            // Save the updated cart
            cartRepository.save(cart);
            
            // Prepare successful response
            response.setStatusCode(200); // Success
            response.setCart(cart);
        } catch (Exception e) {
            response.setStatusCode(500); // Error
            response.setMessage("Error updating cart: " + e.getMessage());
        }
        
        return response;
    }
}
