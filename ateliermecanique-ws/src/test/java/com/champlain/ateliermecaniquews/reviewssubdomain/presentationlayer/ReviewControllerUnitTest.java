package com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.reviewssubdomain.businesslayer.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewControllerUnitTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    private ReviewResponseModel testReview;

    private ReviewRequestModel testReviewRequest;

    @BeforeEach
    void setUp() {
        testReview = ReviewResponseModel.builder()
                .reviewId("testReviewId")
                .customerId("testCustomerId")
                .appointmentId("testAppointmentId")
                .comment("Great service")
                .rating(5.0)
                .reviewDate(LocalDateTime.now())
                .mechanicReply("Thank you for your feedback!")
                .build();
    }

    @Test
    void getAllReviews_shouldReturnReviews() {
        when(reviewService.getAllReviews()).thenReturn(List.of(testReview));

        ResponseEntity<List<ReviewResponseModel>> response = reviewController.getAllReviews();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(testReview, response.getBody().get(0));
    }

    @Test
    void getAllReviews_noReviewsFound_shouldReturnNotFound() {
        when(reviewService.getAllReviews()).thenReturn(Collections.emptyList());

        ResponseEntity<List<ReviewResponseModel>> response = reviewController.getAllReviews();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getReviewById_shouldReturnReview() {
        when(reviewService.getReviewByReviewId("testReviewId")).thenReturn(testReview);

        ResponseEntity<ReviewResponseModel> response = reviewController.getReviewById("testReviewId");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testReview, response.getBody());
    }

    @Test
    void getReviewById_notFound_shouldReturnNotFound() {
        when(reviewService.getReviewByReviewId("nonexistentReviewId")).thenReturn(null);

        ResponseEntity<ReviewResponseModel> response = reviewController.getReviewById("nonexistentReviewId");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateReview_shouldReturnUpdatedReview() {

        testReviewRequest = ReviewRequestModel.builder()
                .customerId("testCustomerId")
                .appointmentId("testAppointmentId")
                .comment("Great service")
                .rating(5.0)
                .reviewDate(LocalDateTime.now())
                .build();

        when(reviewService.updateReview("testReviewId", testReviewRequest)).thenReturn(testReview);

        ResponseEntity<ReviewResponseModel> response = reviewController.updateReview("testReviewId", testReviewRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testReview, response.getBody());

    }
}
