package com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer;


import com.champlain.ateliermecaniquews.reviewssubdomain.businesslayer.ReviewsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class ReviewController {

    final private ReviewsService reviewsService;


    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponseModel>> getAllReviews() {
        List<ReviewResponseModel> reviews = reviewsService.getAllReviews();
        if (reviews == null || reviews.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(reviews);

    }

}
