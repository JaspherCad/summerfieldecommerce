package com.example.hoteltest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hoteltest.dto.ProductDTO;
import com.example.hoteltest.dto.Response;
import com.example.hoteltest.dto.StoreResponseDTO;
import com.example.hoteltest.dto.UserDTO;
import com.example.hoteltest.exceptiom.MyCustomException;
import com.example.hoteltest.model.Product;
import com.example.hoteltest.model.Store;
import com.example.hoteltest.model.Tag;
import com.example.hoteltest.model.User;
import com.example.hoteltest.repository.ProductRepository;
import com.example.hoteltest.repository.StoreRepository;
import com.example.hoteltest.repository.TagRepository;
import com.example.hoteltest.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

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

    // Method for adding a product by a seller to a specific store
    public Response addProduct(ProductDTO productDTO, String email) {
        Response response = new Response();
        try {
            // Check if the email exists, then get the seller
            User seller = userRepository.findByEmail(email)
                    .orElseThrow(() -> new MyCustomException(email + ": not found"));

            if (seller.getStore() == null) {
                throw new MyCustomException("Seller has no store yet, create one");

            }
            // Ensure the user is a seller
            if (seller.getRole() != "SELLER") {
                throw new MyCustomException("User is not a seller");
            }

            // Fetch the store based on storeId from productDTO
            Store store = storeRepository.findById(seller.getStore().getId())
                    .orElseThrow(() -> new MyCustomException(email + ": not found"));


            if (!store.getUser().getId().equals(seller.getId())) {
                throw new MyCustomException("Store does not belong to the seller");
            }

            // Create and populate the product entity
            
            Product product = new Product();
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            product.setQuantity(productDTO.getQuantity());
            product.setCategory(productDTO.getCategory());
            product.setStore(store);  // Associate the product with the seller's store
            product.setFeatured(false);
            List<Tag> listOfTags = new ArrayList<>();
            for (String tagName : productDTO.getTagNames()) {
                // Find the tag by name, or create it if it doesn't exist
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> {
                            Tag newTag = new Tag();
                            newTag.setName(tagName);
                            newTag.setWhoCreatedTheTagHuh(seller.getEmail());
                            return tagRepository.save(newTag); // Save new tag to database
                        });

                // Add the tag to the product's tags list if it's not already there
                product.getTags().add(tag);
                
            }


//            List<Tag> tags = productDTO.getTagNames().stream()
//            	    .map(tagName -> tagRepository.findByName(tagName)) // Find by name returns an Optional<Tag>
//            	    .filter(optionalTag -> optionalTag.isPresent()) // Filter out empty optionals
//            	    .map(optionalTag -> optionalTag.get()) // Get the value from non-empty optionals
//            	    .collect(Collectors.toList());
//
//            product.setTags(tags);
            product.setImgSrc(productDTO.getImgSrc());
            // Save the product to the database
           
            storeRepository.save(store);
            productRepository.save(product);

            // Set the success response
            response.setStatusCode(201);
            response.setMessage("Product added successfully to store: " + store.getName());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error during product addition: " + e.getMessage());
        }
        return response;
    }

    // Method for getting a specific product by product ID
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
    public Response getProductOfSpecificUser(Long sellerId) {
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

        } catch (Exception e) {
            response.setStatusCode(404);
            response.setMessage("Product not found: " + e.getMessage());
        }

        return response;
    }

    // Method for updating a product
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
            
            
            List<Tag> listOfTags = new ArrayList<>();
            for (String tagName : productDTO.getTagNames()) { //since getTAgNames is string...
                // Find the tag by name, or create it if it doesn't exist
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> {
                            Tag newTag = new Tag();
                            newTag.setName(tagName);
                            newTag.setWhoCreatedTheTagHuh(currentUser.getEmail());
                            return tagRepository.save(newTag); // Save new tag to database
                        });

                // Add the tag to the product's tags list if it's not already there
                if (!product.getTags().contains(tag)) {
                	listOfTags.add(tag);
                }
            }
            product.setTags(listOfTags);
            // Save updated product to the repository
            productRepository.save(product);

            // Set success response
            response.setStatusCode(200);
            response.setMessage("Product updated successfully");

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
    
    public List<ProductDTO> getProductsByTagName(String tagName){
    	List<Product> products = productRepository.findByTagName(tagName.toLowerCase());
    	return products.stream()
    			.map(ProductDTO::new)
    	        .collect(Collectors.toList());

    }
    
    public List<Product> getFeaturedProducts() {
        return productRepository.findFeaturedProducts();
    }
    
    public List<Product> getNewArrivals(int limit) {
        Pageable pageable = PageRequest.of(0, limit);  // Get 'limit' number of newest products
        return productRepository.findNewArrivals(pageable);
    }
    
    public List<Product> getBestSellingProducts(int limit) {
        Pageable pageable = PageRequest.of(0, limit);  // Get 'limit' number of best sellers
        return productRepository.findBestSellingProducts(pageable);
    }
    
}

