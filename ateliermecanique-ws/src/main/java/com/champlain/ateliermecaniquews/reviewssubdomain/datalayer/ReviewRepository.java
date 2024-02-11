package com.champlain.ateliermecaniquews.reviewssubdomain.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Review findReviewByReviewIdentifier_ReviewId(String reviewId);
}
