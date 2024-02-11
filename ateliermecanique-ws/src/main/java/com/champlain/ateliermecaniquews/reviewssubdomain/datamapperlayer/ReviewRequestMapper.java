package com.champlain.ateliermecaniquews.reviewssubdomain.datamapperlayer;

import com.champlain.ateliermecaniquews.reviewssubdomain.datalayer.Review;
import com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer.ReviewRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reviewIdentifier", ignore = true)
    Review requestModelToEntity(ReviewRequestModel reviewRequestModel);
}