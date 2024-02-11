package com.champlain.ateliermecaniquews.reviewssubdomain.businesslayer;


import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import com.champlain.ateliermecaniquews.reviewssubdomain.datalayer.ReviewRepository;
import com.champlain.ateliermecaniquews.reviewssubdomain.datamapperlayer.ReviewRequestMapper;
import com.champlain.ateliermecaniquews.reviewssubdomain.datamapperlayer.ReviewResponseMapper;
import com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer.ReviewResponseModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private UserRepository userRepository;
    private ReviewResponseMapper reviewResponseMapper;
    private ReviewRequestMapper reviewRequestMapper;


    @Override
    public List<ReviewResponseModel> getAllReviews() {
        return reviewResponseMapper.entityToResponseModelList(reviewRepository.findAll());
    }

    @Override
    public ReviewResponseModel getReviewByReviewId(String reviewId) {
        return reviewResponseMapper.entityToResponseModel(reviewRepository.findReviewByReviewIdentifier_ReviewId(reviewId));
    }
}
