package com.champlain.ateliermecaniquews.reviewssubdomain.datamapperlayer;

import com.champlain.ateliermecaniquews.reviewssubdomain.datalayer.Reviews;
import com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer.ReviewResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewResponseMapper {

    @Mapping(expression = "java(review.getReviewIdentifier().getReviewId())", target = "reviewId")
    ReviewResponseModel entityToReviewResponseModel(Reviews review);

    List<ReviewResponseModel> entityToReviewResponseModelList(List<Reviews> reviews);
}
