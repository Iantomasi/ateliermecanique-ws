package com.champlain.ateliermecaniquews.reviewssubdomain.datamapperlayer;

import com.champlain.ateliermecaniquews.reviewssubdomain.datalayer.Review;
import com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer.ReviewResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewResponseMapper {

    @Mapping(expression = "java(review.getReviewIdentifier().getReviewId())", target = "reviewId")
    @Mapping(target = "customerId", source = "review.customerId")
    @Mapping(target = "appointmentId", source = "review.appointmentId")

    ReviewResponseModel entityToResponseModel(Review review);

    List<ReviewResponseModel> entityToResponseModelList(List<Review> reviews);

}
