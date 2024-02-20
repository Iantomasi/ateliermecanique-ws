package com.champlain.ateliermecaniquews.reviewssubdomain.businesslayer;

import com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer.ReviewRequestModel;
import com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer.ReviewResponseModel;

import java.util.List;

public interface ReviewService {

    List<ReviewResponseModel> getAllReviews();
    ReviewResponseModel getReviewByReviewId(String reviewId);
    List<ReviewResponseModel> getReviewsByCustomerId(String customerId);
    ReviewResponseModel updateReview(String reviewId, ReviewRequestModel reviewResponseModel);
    ReviewResponseModel updateMechanicReply(String reviewId, String mechanicReply);

    ReviewResponseModel addReview(ReviewRequestModel reviewRequestModel);
    void deleteReviewByReviewId(String reviewId);
    boolean isOwnerOfReview(String authenticatedUserId, String reviewId);
}
