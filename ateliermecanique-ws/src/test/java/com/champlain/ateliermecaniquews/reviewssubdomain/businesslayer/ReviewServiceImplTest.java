package com.champlain.ateliermecaniquews.reviewssubdomain.businesslayer;

import com.champlain.ateliermecaniquews.reviewssubdomain.datalayer.Review;
import com.champlain.ateliermecaniquews.reviewssubdomain.datalayer.ReviewRepository;
import com.champlain.ateliermecaniquews.reviewssubdomain.datamapperlayer.ReviewRequestMapper;
import com.champlain.ateliermecaniquews.reviewssubdomain.datamapperlayer.ReviewResponseMapper;
import com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer.ReviewRequestModel;
import com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer.ReviewResponseModel;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewResponseMapper reviewResponseMapper;

    @Mock
    private ReviewRequestMapper reviewRequestMapper; // Assuming you might need it for future tests

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    void getAllReviews_shouldSucceed() {
        // Arrange
        Review review = new Review(); // Assume this is populated as necessary
        List<Review> reviews = Collections.singletonList(review);
        when(reviewRepository.findAll()).thenReturn(reviews);

        ReviewResponseModel responseModel = ReviewResponseModel.builder()
                .reviewId("reviewId1")
                .customerId("customerId1")
                .appointmentId("appointmentId1")
                .comment("Great service")
                .rating(5.0)
                .reviewDate(LocalDateTime.now())
                .mechanicReply("Thank you for your feedback!")
                .build();
        List<ReviewResponseModel> responseModels = Collections.singletonList(responseModel);
        when(reviewResponseMapper.entityToResponseModelList(reviews)).thenReturn(responseModels);

        // Act
        List<ReviewResponseModel> result = reviewService.getAllReviews();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(responseModels.size(), result.size());
        assertEquals(responseModels.get(0), result.get(0));

        verify(reviewRepository, times(1)).findAll();
        verify(reviewResponseMapper, times(1)).entityToResponseModelList(reviews);
    }

    @Test
    void getReviewByReviewId_shouldSucceed() {
        // Arrange
        String reviewId = "reviewId1";
        Review review = new Review(); // Assume this is populated as necessary
        when(reviewRepository.findReviewByReviewIdentifier_ReviewId(reviewId)).thenReturn(review);

        ReviewResponseModel expectedResponse = ReviewResponseModel.builder()
                .reviewId(reviewId)
                .customerId("customerId1")
                .appointmentId("appointmentId1")
                .comment("Very professional")
                .rating(4.5)
                .reviewDate(LocalDateTime.now())
                .mechanicReply("We appreciate your business!")
                .build();
        when(reviewResponseMapper.entityToResponseModel(review)).thenReturn(expectedResponse);

        // Act
        ReviewResponseModel result = reviewService.getReviewByReviewId(reviewId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResponse, result);

        verify(reviewRepository, times(1)).findReviewByReviewIdentifier_ReviewId(reviewId);
        verify(reviewResponseMapper, times(1)).entityToResponseModel(review);
    }

    @Test
    void getAllReviews_noReviewsFound_shouldReturnEmptyList() {
        // Arrange
        when(reviewRepository.findAll()).thenReturn(Collections.emptyList());
        when(reviewResponseMapper.entityToResponseModelList(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Act
        List<ReviewResponseModel> result = reviewService.getAllReviews();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(reviewRepository, times(1)).findAll();
        verify(reviewResponseMapper, times(1)).entityToResponseModelList(Collections.emptyList());
    }

    @Test
    void getReviewByReviewId_notFound_shouldReturnEmptyList() {
        // Arrange
        String reviewId = "nonExistentReviewId";
        when(reviewRepository.findReviewByReviewIdentifier_ReviewId(reviewId)).thenReturn(null);

        // Act
        ReviewResponseModel result = reviewService.getReviewByReviewId(reviewId);

        // Assert
        assertNull(result);

        verify(reviewRepository, times(1)).findReviewByReviewIdentifier_ReviewId(reviewId);
        verify(reviewResponseMapper, never()).entityToResponseModel(any(Review.class));
    }

    @Test
    void updateReview_shouldSucceed() {

        // Arrange
        String reviewId = "reviewId1";
        Review review = new Review();

        String customerId = "test-customer-id";
        String appointmentId = "test-appointment-id";
        LocalDateTime reviewDate = LocalDateTime.parse("2024-01-02T19:00"); //2024-01-02T19:00;
        String comment = "Initial Notes";
        Double rating = 150.00;

        ReviewRequestModel requestModel = new ReviewRequestModel(
                "test-customer-id",
                "test-appointment-id",
                "Initial Notes",
                150.00,
                LocalDateTime.parse("2024-01-02T19:00")

        );
        when(reviewRepository.findReviewByReviewIdentifier_ReviewId(reviewId)).thenReturn(review);

        // Act
        reviewService.updateReview(reviewId, requestModel);

        // Assert
        verify(reviewRepository).findReviewByReviewIdentifier_ReviewId(reviewId);
        verify(reviewRepository).save(review);

        assertEquals(customerId, review.getCustomerId());
        assertEquals(appointmentId, review.getAppointmentId());
        assertEquals(reviewDate, review.getReviewDate());
        assertEquals(comment, review.getComment());
        assertEquals(rating, review.getRating());

    }
}
