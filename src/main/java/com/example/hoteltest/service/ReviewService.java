package com.example.hoteltest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hoteltest.dto.ProductDTO;
import com.example.hoteltest.dto.Response;
import com.example.hoteltest.dto.ReviewDTO;
import com.example.hoteltest.exceptiom.MyCustomException;
import com.example.hoteltest.model.Product;
import com.example.hoteltest.model.Review;
import com.example.hoteltest.model.User;
import com.example.hoteltest.repository.OrderItemRepository;
import com.example.hoteltest.repository.ProductRepository;
import com.example.hoteltest.repository.ReviewRepository;
import com.example.hoteltest.repository.UserRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    private void updateAverageRating(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        double average = reviews.stream()
                                .mapToInt(Review::getRating) //prolly returns int
                                .average()
                                .orElse(0.0);

        Product product = productRepository.findById(productId).get();
        product.setAverageRating(average);
        productRepository.save(product);
    }
    
    //create DTO and try/catch
    public ReviewDTO addReview(Long productId, Long userId, Integer rating, String comment) {
    	
    	if (!orderItemRepository.didUserPurchasedProduct(userId, productId)) {
            throw new MyCustomException("User has not purchased this product and cannot leave a review.");
    	}
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Review review = new Review();
        review.setProduct(product);
        review.setUser(user);
        review.setRating(rating);
        review.setComment(comment);

        Review savedReview = reviewRepository.save(review);
        updateAverageRating(savedReview.getProduct().getId());

        return new ReviewDTO(savedReview);
    }
    

    public List<ReviewDTO> getReviewsByProduct(Long productId) {
    	List<Review> reviews = reviewRepository.findByProductId(productId);
    	

    	List<ReviewDTO> reviewDTOs = reviews.stream()
    			.map(ReviewDTO::new)
    			.collect(Collectors.toList());
        return reviewDTOs;
    }

    public List<Review> getReviewsByUser(Long userId) {
        return reviewRepository.findByUserId(userId);
    }
    
    public Response deletReview(Long reviewId) {
    	Response response = new Response();
    	try {
    		Review review = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
        	
        	reviewRepository.delete(review);
        	 response.setStatusCode(200);
             response.setMessage("Product deleted successfully");
             
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

}
