package com.example.hoteltest.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.hoteltest.dto.HomePageProductDTO;
import com.example.hoteltest.dto.ProductDTO;
import com.example.hoteltest.dto.Response;
import com.example.hoteltest.model.Product;
import com.example.hoteltest.model.User;
import com.example.hoteltest.service.ProductService;
import com.example.hoteltest.service.ProductUpdateEvent;
import com.example.hoteltest.service.sellerdashboard.TopProductDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	
    private final List<Consumer<ProductDTO>> subscribers = new CopyOnWriteArrayList<>();
    		//each subscribers who accepted (event.getProductDto()));)
    		//subscribe has list of = [consumer, consumer, consumer, consumer]
	
    
    
    
    
    
    //    2: Handling the Event: In your ProductController, you have an event listener that listens for ProductUpdateEvent:
    //this HAS BEEN TRIGGERED after adding product from productService
    //When ProductUpdateEvent is published, this method is automatically triggered.

    @EventListener
    public void handleProductUpdateEvent(ProductUpdateEvent event) {
        subscribers.forEach(consumer -> consumer.accept(event.getProductDto()));
    }
    
    
    
  //3: (remember, 1: was pushing the ProductDTO to ProductUpdateEvent at ProductService.java
  	//3: --
    
   
    //SO WHAT IS THIS?? sa react, if the jsx subscribers to 8080/product/updates;;; dito mapupunta logic/
    //what this do? bascically we just LISTEN on HOW THE (1 & 2 insctrution) WAS CREATED. /updates JUST FETCH and send to react js THIS ALL
    @GetMapping(value = "/updates", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDTO> getProductUpdates() {
        return Flux.create(sink -> {
            Consumer<ProductDTO> consumer = sink::next;
            subscribers.add(consumer);
            sink.onCancel(() -> subscribers.remove(consumer));
        });
    }

    

	
    
    
    
//    Summary of the Flow
//    Step 1: A product is added via the addProduct method.
//    Step 2: ProductUpdateEvent is created and published using ApplicationEventPublisher.
//    Step 3: The event listener (handleProductUpdateEvent) reacts to the published event and notifies all subscribers (clients connected to the SSE endpoint).
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @GetMapping("/top-products/limit/{limit}")
    public List<TopProductDTO> topProducts(@PathVariable int limit){
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        
    	return productService.getBestSellingProductOfAShopByShopId(currentUser.getStore().getId(), limit);
    }
    
	@PostMapping("/add")
    @PreAuthorize("hasAuthority('SELLER')")
	public ResponseEntity<Response> addProduct(@RequestBody ProductDTO productDTO, @AuthenticationPrincipal User user){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        
		
		Response response = productService.addProduct(productDTO, user.getEmail());
        return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
//	@GetMapping(value = "/updates", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public SseEmitter getProductUpdates() {
//        SseEmitter emitter = productService.registerEmitter();
//        // Set header explicitly
//        try {
//            emitter.send(SseEmitter.event().reconnectTime(5000)); // Set reconnection time on the client side
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return emitter;
//	}
	
    
	
	@GetMapping("/{productId}")
    public ResponseEntity<Response> getProduct(@PathVariable Long productId) {
        Response response = productService.getProduct(productId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
	
	@GetMapping("/seller/{sellerId}")
	//get using path variable the seller id
	//after getting the seller id, send it to the productSeviceTOgeet spcificProducf
	
	public ResponseEntity<Response> getProductOfSpecifcUser(@PathVariable Long sellerId, @AuthenticationPrincipal User currentUser){ //serialization issue
		
    
	    // Use `currentUser` directly without needing to access `SecurityContextHolder`
			//coz getting product without authorization may cause string like "annonymouse" and not a class.
	    System.out.println("Current User at getProductOfSpecifcUser: " + currentUser);
	    Response response = productService.getProductOfSpecificUser(sellerId, currentUser);
	   
        return ResponseEntity.status(response.getStatusCode()).body(response);
	    
	    
	}
	
	//similar to deleteMapping
	@PutMapping("/{productId}")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<Response> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        
		Response response = productService.updateProduct(productId, productDTO, currentUser);
        return ResponseEntity.status(response.getStatusCode()).body(response);

	}
	
	@DeleteMapping("/delete/{productId}")
	@PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<Response> deleteProduct(@PathVariable Long productId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        
		Response response = productService.deleteProductOfSeller(productId, currentUser);
        return ResponseEntity.status(response.getStatusCode()).body(response);

	}
	
	
	@PostMapping("/{productId}/tags")
	public ResponseEntity<Response> addTagsToProduct(
	        @PathVariable Long productId, 
	        @RequestBody List<String> tagNames) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        
	    
	    Response response = productService.addTagsToProduct(productId, tagNames, currentUser);
	    return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	
	@GetMapping("/filter/{tagName}")
    public ResponseEntity<List<ProductDTO>> getProductsByTagName(@PathVariable String tagName) {
		List<ProductDTO> products = productService.getProductsByTagName(tagName);
        return ResponseEntity.ok(products);
    }
	
	
	
	
	
	
	
	
	
	@GetMapping("/homepage")
    public ResponseEntity<List<ProductDTO>> getHomePageProducts() {
        List<Product> featuredProducts = productService.getFeaturedProducts();
        List<Product> newArrivals = productService.getNewArrivals(10); // Limit to 10 new arrivals
        List<Product> bestSellers = productService.getBestSellingProducts(10); // Limit to 10 best sellers
        
        //convert these 3 into dto before we send
        
        List<ProductDTO> featuredProductDto = featuredProducts.stream()
                .map(ProductDTO::new) //inside dto should not have any LIST<>
                .collect(Collectors.toList());
        
        List<ProductDTO> newArrivalsDto = newArrivals.stream()
                .map(ProductDTO::new) //inside dto should not have any LIST<>
                .collect(Collectors.toList());
        
        List<ProductDTO> bestSellersDto = bestSellers.stream()
                .map(ProductDTO::new) //inside dto should not have any LIST<>
                .collect(Collectors.toList());
        
        
     // Combine all DTOs into a single list
        List<ProductDTO> combinedProductDto = new ArrayList<>();
        combinedProductDto.addAll(featuredProductDto);
        combinedProductDto.addAll(newArrivalsDto);
        combinedProductDto.addAll(bestSellersDto);

        // Return combined list
        return ResponseEntity.ok(combinedProductDto);
    }

	
	//test

	
	
}
