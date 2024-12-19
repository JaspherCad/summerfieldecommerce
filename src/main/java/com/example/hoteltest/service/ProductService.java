package com.example.hoteltest.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.data.domain.Page; import org.springframework.data.domain.PageRequest; import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hoteltest.dto.ProductDTO;
import com.example.hoteltest.dto.Response;
import com.example.hoteltest.dto.StoreResponseDTO;
import com.example.hoteltest.dto.UserDTO;
import com.example.hoteltest.exceptiom.MyCustomException;
import com.example.hoteltest.model.Product;
import com.example.hoteltest.model.Store;
import com.example.hoteltest.model.Tag;
import com.example.hoteltest.model.User;
import com.example.hoteltest.model.UserLog;
import com.example.hoteltest.repository.ProductRepository;
import com.example.hoteltest.repository.StoreRepository;
import com.example.hoteltest.repository.TagRepository;
import com.example.hoteltest.repository.UserLogRepository;
import com.example.hoteltest.repository.UserRepository;
import com.example.hoteltest.service.sellerdashboard.TopProductDTO;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StoreRepository storeRepository;
    
    @Autowired
    TagRepository tagRepository;
    
    @Autowired
    UserLogRepository userLogRepository;
    
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public ProductService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    
//    Overview of Events in Spring
//    In Spring, the event-driven architecture allows components to communicate with each other without needing direct dependencies. Events provide a way for components to publish messages (events) and for other components to listen and react to those messages.
//
//    1. ApplicationEventPublisher
//    Purpose: The ApplicationEventPublisher is an interface in Spring that allows you to publish events to the application context.
//    Usage: When you want to notify other components that something has happened (e.g., a product has been added), you can use this publisher to send an event.
//    Example of ApplicationEventPublisher
//    In your ProductService, you inject ApplicationEventPublisher to publish events:
    
    
    
    //eventPublisher -> allow us to publish events: example
    //	            eventPublisher.publishEvent(new ProductUpdateEvent(this, newProductDto));  // Publish the event

    
    // Method for adding a product by a seller to a specific store
    @Transactional
    public Response addProduct(ProductDTO productDTO, String email) {
        Response response = new Response();
        try {
            User seller = userRepository.findByEmail(email)
                    .orElseThrow(() -> new MyCustomException(email + ": not found"));

            if (seller.getStore() == null) {
                throw new MyCustomException("Seller has no store yet, create one");

            }
            if (!"SELLER".equals(seller.getRole())) {
            	System.out.println(seller.getRole());
                throw new MyCustomException("User is not a seller");
            }

            Store store = storeRepository.findById(seller.getStore().getId())
                    .orElseThrow(() -> new MyCustomException(email + ": not found"));


            if (!store.getUser().getId().equals(seller.getId())) {
                throw new MyCustomException("Store does not belong to the seller");
            }

            
            Product product = new Product();
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            product.setQuantity(productDTO.getQuantity());
            product.setCategory(productDTO.getCategory());
            product.setStore(store);  // Associate the product with the seller's store
            product.setFeatured(false);
            product.setAverageRating(0.0);
            
            //new
            product.setCost(productDTO.getCost());
            product.setDiscount(productDTO.getDiscount());
            
            BigDecimal priceAfterDiscountSol = calculatePriceAfterDiscount(productDTO.getPrice(), productDTO.getDiscount());
            product.setPriceAfterDiscount(priceAfterDiscountSol);
            
            List<Tag> listOfTags = new ArrayList<>();
            for (String tagName : productDTO.getTagNames()) {
                // Find the tag by name, or create it if it doesn't exist
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> {
                            Tag newTag = new Tag();
                            newTag.setName(tagName);
                            newTag.setWhoCreatedTheTagHuh(seller.getEmail());
                            return tagRepository.save(newTag); // Save new tag to database and returns it
                        });

                // Add the tag to the product's tags list if it's not already there
                product.getTags().add(tag);
                listOfTags.add(tag); //IF ERROR REMOVE THIS
            }


            product.setTags(listOfTags); // Set the tags to the product KASI MANYtoMANY
            product.setImgSrc(productDTO.getImgSrc());
            // Save the product to the database
           
            storeRepository.save(store);
            Product savedProduct = productRepository.save(product);

            // Set the success response
            response.setStatusCode(201);
            response.setMessage("Product added successfully to store: " + store.getName());
            
            String description = "ADDED PRODUCT " + savedProduct.getId();
            UserLog log = new UserLog(seller, "ADD", description, seller.getFullName());
            userLogRepository.save(log);
            
            
            
         // Send real-time update to clients
            //WE ARE SENDING THE SAVED PRODUCTDTO into ProductUpdateEvent (ginawa kong class).
            //WHAT'S NEXT AFTER THIS>>> go to ProductController
            ProductDTO newProductDto = new ProductDTO(savedProduct);
            eventPublisher.publishEvent(new ProductUpdateEvent(this, newProductDto));  // Publish the event
            
            
//            Summary of the Flow
//            Step 1: A product is added via the addProduct method.
//            Step 2: ProductUpdateEvent is created and published using ApplicationEventPublisher.
//            Step 3: The event listener (handleProductUpdateEvent) reacts to the published event and notifies all subscribers (clients connected to the SSE endpoint).

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error during product addition: " + e.getMessage());
        }
        return response;
    }
    
    
    
    @Transactional
    private BigDecimal calculatePriceAfterDiscount(BigDecimal price, BigDecimal discount) { 
    	if (discount == null || discount.compareTo(BigDecimal.ZERO) == 0) { 
    		return price; 
    	} 
    	BigDecimal discountAmount = price.multiply(discount).divide(BigDecimal.valueOf(100)); 
    	return price.subtract(discountAmount); 
    }
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    // Method for getting a specific product by product ID
    @Transactional(readOnly = true)
    public Response getProduct(Long productId) {
        Response response = new Response();
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            response.setStatusCode(200);
            //turn into dto
            ProductDTO productDTO = new ProductDTO(product);
            response.setProductDTO(productDTO);
            
        } catch (Exception e) {
            response.setStatusCode(404);
            response.setMessage("Product not found");
        }
        return response;
    }

    // Method for getting products of a specific STORE!
    @Transactional(readOnly = true)
    public Response getProductOfSpecificUser(Long sellerId, User currentUser) {
        Response response = new Response();
        try {

            User seller = userRepository.findById(sellerId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Store store = storeRepository.findById(seller.getStore().getId())
                    .orElseThrow(() -> new RuntimeException("Store not found"));
            
//            if (!store.getUser().getId().equals(sellerId)) {
//                throw new RuntimeException("Store does not belong to the seller");
//            }

            
            StoreResponseDTO storeResponseDTO = new StoreResponseDTO(store);
            List<Product> listOfProductOfSpecificSeller = productRepository.findByStore(store);
            
            
            
            List<ProductDTO> productDto = listOfProductOfSpecificSeller.stream()
                    .map(ProductDTO::new) //inside dto should not have any LIST<>
                    .collect(Collectors.toList());

         // Log these values to ensure they are present before creating StoreResponseDTO
            
            response.setStatusCode(200);
            response.setListOfProduct(productDto);
            response.setMessage("isGcash?" + store.isGcash());
            response.setStoreResponseDTO(storeResponseDTO);
            //if currentUser is the seller, exempt this.. so if(currentUser != seller).. only then log
            if (currentUser != null && !currentUser.getId().equals(seller.getId())) {
                String description = "VIEWED YOUR PAGE";
                UserLog log = new UserLog(seller, "VIEW", description, currentUser.getFullName());
                userLogRepository.save(log);
            }


        } catch (Exception e) {
            response.setStatusCode(404);
            response.setMessage("Product not found: " + e.getMessage());
        }

        return response;
    }

    // Method for updating a product
    @Transactional
    public Response updateProduct(Long productId, ProductDTO productDTO, User currentUser) {
        Response response = new Response();

        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // Check if the current user is the owner of the store where the product is
//            if (!product.getStore().getSeller().getId().equals(currentUser.getId())) {
//                throw new MyCustomException("You are not the owner of this product");
//            }

            // Update product details
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            product.setQuantity(productDTO.getQuantity());
            product.setCategory(productDTO.getCategory());
            product.setImgSrc(productDTO.getImgSrc());
            product.setCost(productDTO.getCost());
            product.setDiscount(productDTO.getDiscount());
            BigDecimal priceAfterDiscountSol = calculatePriceAfterDiscount(productDTO.getPrice(), productDTO.getDiscount());
            product.setPriceAfterDiscount(priceAfterDiscountSol);
            
            List<Tag> listOfTags = new ArrayList<>();
//            listOfTags.add(null) product.getTags();
            
//            //add the CURRENT EXISTING tags here.
//            for (Tag tag: product.getTags()) {
//            	listOfTags.add(tag);
//            }
            
//            listOfTags.addAll(product.getTags());
            
			//            for (String tagName : productDTO.getTagNames()) { //since getTAgNames is string...
			//                // Find the tag by name, or create it if it doesn't exist
			//                Tag tag = tagRepository.findByName(tagName)
			//                        .orElseGet(() -> {
			//                            Tag newTag = new Tag();
			//                            newTag.setName(tagName);
			//                            newTag.setWhoCreatedTheTagHuh(currentUser.getEmail());
			//                            return tagRepository.save(newTag); // Save new tag to database
			//                        });
			//
			//                // Add the tag to the product's tags list if it's not already there
			//                if (!listOfTags.contains(tag)) { 
			//                	listOfTags.add(tag); 
			//                }
			//            }
            
            
            
            for (String tagName : productDTO.getTagNames()) {
                // Find the tag by name, or create it if it doesn't exist
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> {
                            Tag newTag = new Tag();
                            newTag.setName(tagName);
                            newTag.setWhoCreatedTheTagHuh(currentUser.getEmail());
                            return tagRepository.save(newTag); // Save new tag to database and returns it
                        });

                // TEST: SAME AS TO ADD PRODUCT METHOD. FRONTEND MAG ADJUST
                product.getTags().add(tag);
                listOfTags.add(tag); //IF ERROR REMOVE THIS
            }


            product.setTags(listOfTags); // Set the tags to the product KASI MANYtoMANY
            
            
           
            // Save updated product to the repository
            Product savedProduct = productRepository.save(product);

            // Set success response
            response.setStatusCode(200);
            response.setMessage("Product updated successfully");
            
         // Send real-time update to clients
            ProductDTO newProductDto = new ProductDTO(savedProduct);
            eventPublisher.publishEvent(new ProductUpdateEvent(this, newProductDto));  // Publish the event
            
            String description = "UPDATED PRODUCT " + product.getId();
            UserLog log = new UserLog(product.getStore().getUser(), "UPDATE", description, currentUser.getFullName());
            userLogRepository.save(log);

        } catch (MyCustomException e) {
            // Handle custom exception for unauthorized update attempt
            response.setStatusCode(403); // Forbidden
            response.setMessage(e.getMessage());
        } catch (RuntimeException e) {
            // Handle not found or any other exception
            response.setStatusCode(404); // Not found
            response.setMessage("Product not found");
        } catch (Exception e) {
            // Handle any unexpected exception
            response.setStatusCode(500); // Internal Server Error
            response.setMessage("Error updating product: " + e.getMessage());
        }

        return response;
    }

    // Method for deleting a product belonging to a specific seller
    @Transactional
    public Response deleteProductOfSeller(Long productId, User seller) {
        Response response = new Response();
        try {
            Product productToBeDeleted = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // Ensure the seller is the owner of the store where the product belongs
//            if (!productToBeDeleted.getStore().getSeller().getId().equals(seller.getId())) {
//                throw new MyCustomException("You are not the owner of this product");
//            }

            // Delete the product
            productRepository.delete(productToBeDeleted);

            // Set the success response
            response.setStatusCode(200);
            response.setMessage("Product deleted successfully");
            response.setProduct(productToBeDeleted);
            
            String description = "DELETED PRODUCT " + productToBeDeleted.getId();
            UserLog log = new UserLog(productToBeDeleted.getStore().getUser(), "DELETE", description, seller.getFullName());
            userLogRepository.save(log);

        } catch (MyCustomException e) {
            // Handle custom exception for unauthorized deletion attempt
            response.setStatusCode(403); // Forbidden
            response.setMessage(e.getMessage());
        } catch (RuntimeException e) {
            // Handle not found or any other exception
            response.setStatusCode(404); // Not found
            response.setMessage("Product not found");
        } catch (Exception e) {
            // Handle any unexpected exception
            response.setStatusCode(500); // Internal Server Error
            response.setMessage("Error deleting product: " + e.getMessage());
        }

        return response;
    }
    @Transactional
    public Response addTagsToProduct(Long productId, List<String> tagNames, User user) {
    	Response response = new Response();
        
        try {
            // Find the product by ID
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            for (String tagName : tagNames) {
                // Find the tag by name, or create it if it doesn't exist
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> {
                            Tag newTag = new Tag();
                            newTag.setName(tagName);
                            newTag.setWhoCreatedTheTagHuh(user.getEmail());
                            return tagRepository.save(newTag); // Save new tag to database
                        });

                // Add the tag to the product's tags list if it's not already there
                if (!product.getTags().contains(tag)) {
                    product.getTags().add(tag);
                }
            }

            // Save the updated product with the new tags
            productRepository.save(product);

            response.setStatusCode(200);
            response.setMessage("Tags added successfully");

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error adding tags: " + e.getMessage());
        }

        return response;
    }
    @Transactional
    public List<ProductDTO> getProductsByTagName(String tagName){
    	List<Product> products = productRepository.findByTagName(tagName.toLowerCase());
    	return products.stream()
    			.map(ProductDTO::new)
    	        .collect(Collectors.toList());

    }
    @Transactional(readOnly = true)
    public List<Product> getFeaturedProducts() {
        return productRepository.findFeaturedProducts();
    }
    @Transactional(readOnly = true)
    public List<Product> getNewArrivals(int limit) {
        Pageable pageable = PageRequest.of(0, limit);  
        return productRepository.findNewArrivals(pageable);
    }
    @Transactional(readOnly = true)
    public List<Product> getBestSellingProducts(int limit) {
        Pageable pageable = PageRequest.of(0, limit);  
        return productRepository.findBestSellingProducts(pageable);
    }
    @Transactional(readOnly = true)
    public List<TopProductDTO> getBestSellingProductOfAShopByShopId(long storeId, int limit){ //can be top 5
        Pageable pageable = PageRequest.of(0, limit);
    	Page<TopProductDTO> top5ProductPage = productRepository.getBestSellingProductOfAShopByShopId(storeId, pageable);
    	
    	return top5ProductPage.getContent();
    }
    
}

