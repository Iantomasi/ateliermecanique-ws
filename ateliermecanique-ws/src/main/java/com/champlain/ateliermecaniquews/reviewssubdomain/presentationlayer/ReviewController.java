package com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer;


import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import com.champlain.ateliermecaniquews.authenticationsubdomain.utils.security.services.UserDetailsImpl;
import com.champlain.ateliermecaniquews.reviewssubdomain.businesslayer.ReviewService;
import lombok.AllArgsConstructor;
import lombok.Generated;
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
    //@PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<ReviewResponseModel> updateReview(@PathVariable String reviewId, @RequestBody ReviewRequestModel reviewRequestModel) {

        ReviewResponseModel review = reviewService.updateReview(reviewId, reviewRequestModel);
        if (review == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(review);
    }

    //todo add get all reviews for a specific customer
    @GetMapping("/reviews/customer/{customerId}")
    public ResponseEntity<List<ReviewResponseModel>> getReviewsByCustomerId(@PathVariable String customerId) {
        List<ReviewResponseModel> reviews = reviewService.getReviewsByCustomerId(customerId);
        if (reviews == null || reviews.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(reviews);
    }


    @DeleteMapping("/reviews/{reviewId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<Void> deleteReviewById(@PathVariable String reviewId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean isCustomer = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"));

            if (!isCustomer) {
                // If it's an admin, no need to check the user against the review
                reviewService.deleteReviewByReviewId(reviewId);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                // It's a customer, verify ownership of the review
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                String authenticatedUserId = userDetails.getUserId();
                boolean isOwner = reviewService.isOwnerOfReview(authenticatedUserId, reviewId);
                if (isOwner) {
                    reviewService.deleteReviewByReviewId(reviewId);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{reviewId}/reply")
    @PreAuthorize("hasRole('ADMIN')")
    @Generated
    public ResponseEntity<ReviewResponseModel> updateMechanicReply(@PathVariable String reviewId, @RequestBody String mechanicReply) {
        ReviewResponseModel updatedReview = reviewService.updateMechanicReply(reviewId, mechanicReply);
        if (updatedReview == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedReview);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/reviews")
    public ResponseEntity<ReviewResponseModel> addReview(@RequestBody ReviewRequestModel reviewRequestModel) {

        // Add the review
        ReviewResponseModel addedReview = reviewService.addReview(reviewRequestModel);

        // Check if the review was successfully added
        if (addedReview != null) {
            // Return the review details with a 201 Created status
            return ResponseEntity.status(HttpStatus.CREATED).body(addedReview);
        } else {
            // Handle the case where the review couldn't be added by the user
            // This could be due to a validation error or an issue with the service
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @PostMapping("/reviews/{reviewId}/newReview")
    public ResponseEntity<ReviewResponseModel> addReview(@PathVariable String reviewId, @RequestBody ReviewRequestModel reviewRequestModel) {

        // Add the review
        ReviewResponseModel addedReview = reviewService.addReview(reviewRequestModel);

        // Check if the review was successfully added
        if (addedReview != null) {
            // Return the review details with a 201 Created status
            return ResponseEntity.status(HttpStatus.CREATED).body(addedReview);
        } else {
            // Handle the case where the review couldn't be added by the user
            // This could be due to a validation error or an issue with the service
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }





}
