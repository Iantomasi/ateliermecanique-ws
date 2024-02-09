package com.champlain.ateliermecaniquews.reviewssubdomain.datalayer;

import jakarta.persistence.Embeddable;

@Embeddable
public class ReviewIdentifier {

    private String reviewId;

    public ReviewIdentifier(){

        this.reviewId = java.util.UUID.randomUUID().toString();
    }

    public String getReviewId(){
        return reviewId;
    }
}
