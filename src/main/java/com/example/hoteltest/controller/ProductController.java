package com.example.hoteltest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hoteltest.dto.HomePageProductDTO;
import com.example.hoteltest.dto.ProductDTO;
import com.example.hoteltest.dto.Response;
import com.example.hoteltest.model.Product;
import com.example.hoteltest.model.User;
import com.example.hoteltest.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@PostMapping("/add")
    @PreAuthorize("hasAuthority('SELLER')")
	public ResponseEntity<Response> addProduct(@RequestBody ProductDTO productDTO, @AuthenticationPrincipal User user){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        
		
		Response response = productService.addProduct(productDTO, user.getEmail());
        return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@GetMapping("/{productId}")
    public ResponseEntity<Response> getProduct(@PathVariable Long productId) {
        Response response = productService.getProduct(productId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
	
	@GetMapping("/seller/{sellerId}")
	//get using path variable the seller id
	//after getting the seller id, send it to the productSeviceTOgeet spcificProducf
	
	public ResponseEntity<Response> getProductOfSpecifcUser(@PathVariable Long sellerId){ //serialization issue
		
			    
	    Response response = productService.getProductOfSpecificUser(sellerId);
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
