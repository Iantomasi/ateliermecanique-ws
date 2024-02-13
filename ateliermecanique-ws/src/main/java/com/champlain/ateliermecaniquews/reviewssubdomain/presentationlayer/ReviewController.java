package com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer;


import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import com.champlain.ateliermecaniquews.reviewssubdomain.businesslayer.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class ReviewController {

    final private ReviewService reviewService;
    final private UserRepository userRepository;


    @GetMapping("/reviews")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<List<ReviewResponseModel>> getAllReviews() {
        List<ReviewResponseModel> reviews = reviewService.getAllReviews();
        if (reviews == null || reviews.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(reviews);

    }

    @GetMapping("/reviews/{reviewId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<ReviewResponseModel> getReviewById(@PathVariable String reviewId) {
        ReviewResponseModel review = reviewService.getReviewByReviewId(reviewId);
        if (review == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(review);
    }

    @PutMapping("/reviews/{reviewId}")
    @PreAuthorize(" hasRole('CUSTOMER')")
    public ResponseEntity<ReviewResponseModel> updateReview(@PathVariable String reviewId, @RequestBody ReviewRequestModel reviewRequestModel) {

        ReviewResponseModel review = reviewService.updateReview(reviewId, reviewRequestModel);
        if (review == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(review);
    }

}
