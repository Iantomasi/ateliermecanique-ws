package com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class ReviewRequestModel {
    private String customer_name;
    private String comment;
    private Double rating;
}
