package com.example.hoteltest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.hoteltest.dto.ReviewDTO;
import com.example.hoteltest.model.Review;
import com.example.hoteltest.service.ReviewService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/product/{productId}/user/{userId}")
    public ReviewDTO addReview(@PathVariable Long productId, @PathVariable Long userId, 
                            @RequestBody Map<String, Object> payload) {
    	String comment = payload.get("comment").toString();
	    int rating = Integer.parseInt(payload.get("rating").toString());
	    

        return reviewService.addReview(productId, userId, rating, comment);
    }

    @GetMapping("/product/{productId}")
    public List<ReviewDTO> getReviewsByProduct(@PathVariable Long productId) {
        return reviewService.getReviewsByProduct(productId);
    }

    @GetMapping("/user/{userId}")
    public List<Review> getReviewsByUser(@PathVariable Long userId) {
        return reviewService.getReviewsByUser(userId);
    }
}
