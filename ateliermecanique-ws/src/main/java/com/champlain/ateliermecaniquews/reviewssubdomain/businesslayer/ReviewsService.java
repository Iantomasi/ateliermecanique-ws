package com.champlain.ateliermecaniquews.reviewssubdomain.businesslayer;

import com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer.ReviewResponseModel;

import java.util.List;

public interface ReviewsService {

    List<ReviewResponseModel> getAllReviews();
}
