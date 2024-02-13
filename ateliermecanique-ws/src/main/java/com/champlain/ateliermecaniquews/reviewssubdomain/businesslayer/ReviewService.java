package com.champlain.ateliermecaniquews.reviewssubdomain.businesslayer;

import com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer.ReviewRequestModel;
import com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer.ReviewResponseModel;

import java.util.List;

public interface ReviewService {

    List<ReviewResponseModel> getAllReviews();
    ReviewResponseModel getReviewByReviewId(String reviewId);




    ReviewResponseModel updateReview(String reviewId, ReviewRequestModel reviewResponseModel);
}
