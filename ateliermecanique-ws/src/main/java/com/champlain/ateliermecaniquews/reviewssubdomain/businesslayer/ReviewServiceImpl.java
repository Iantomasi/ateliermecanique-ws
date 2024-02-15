package com.champlain.ateliermecaniquews.reviewssubdomain.businesslayer;


import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import com.champlain.ateliermecaniquews.reviewssubdomain.datalayer.Review;
import com.champlain.ateliermecaniquews.reviewssubdomain.datalayer.ReviewIdentifier;
import com.champlain.ateliermecaniquews.reviewssubdomain.datalayer.ReviewRepository;
import com.champlain.ateliermecaniquews.reviewssubdomain.datamapperlayer.ReviewRequestMapper;
import com.champlain.ateliermecaniquews.reviewssubdomain.datamapperlayer.ReviewResponseMapper;
import com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer.ReviewRequestModel;
import com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer.ReviewResponseModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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


    @Override
    public ReviewResponseModel updateReview(String reviewId, ReviewRequestModel reviewResponseModel) {
        Review review = reviewRepository.findReviewByReviewIdentifier_ReviewId(reviewId);
        if(review == null){
            log.warn("Review not found for review ID: {}", reviewId);
            return null;

        }
        review.setCustomerId(reviewResponseModel.getCustomerId());
        review.setAppointmentId(reviewResponseModel.getAppointmentId());
        review.setComment(reviewResponseModel.getComment());
        review.setRating(reviewResponseModel.getRating());
        review.setReviewDate(reviewResponseModel.getReviewDate());
        review.setMechanicReply(reviewResponseModel.getMechanicReply());
        return reviewResponseMapper.entityToResponseModel(reviewRepository.save(review));
    }

    @Override
    @Transactional
    public ReviewResponseModel updateMechanicReply(String reviewId, String mechanicReply) {
        return reviewRepository.findOptionalReviewByReviewIdentifier_ReviewId(reviewId).map(review -> {
            review.setMechanicReply(mechanicReply);
            Review updatedReview = reviewRepository.save(review);
            return reviewResponseMapper.entityToResponseModel(updatedReview);
        }).orElse(null);
    }

    @Override
    public ReviewResponseModel addReview(ReviewRequestModel reviewRequestModel) {
        Review review = new Review();
        review.setReviewIdentifier(new ReviewIdentifier());
        review.setCustomerId(reviewRequestModel.getCustomerId());
        review.setAppointmentId(reviewRequestModel.getAppointmentId());
        review.setComment(reviewRequestModel.getComment());
        review.setRating(reviewRequestModel.getRating());
        review.setReviewDate(LocalDateTime.now()); //check this
        review.setMechanicReply(null);

        Review savedReview = reviewRepository.save(review);
        return reviewResponseMapper.entityToResponseModel(savedReview);
    }

    @Override
    public void deleteReviewByReviewId(String reviewId) {
        Review review = reviewRepository.findReviewByReviewIdentifier_ReviewId(reviewId);
        if(review != null){
            reviewRepository.delete(review);
        }
    }

    @Override
    public boolean isOwnerOfReview(String authenticatedUserId, String reviewId) {
        return reviewRepository.findReviewByReviewIdentifier_ReviewIdAndCustomerId(reviewId, authenticatedUserId).isPresent();
    }

}
