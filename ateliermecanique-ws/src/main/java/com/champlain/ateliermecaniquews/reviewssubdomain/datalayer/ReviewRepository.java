package com.champlain.ateliermecaniquews.reviewssubdomain.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Review findReviewByReviewIdentifier_ReviewId(String reviewId);
    Optional<Review> findReviewByReviewIdentifier_ReviewIdAndCustomerId(String reviewId, String customerId);

}
