package com.champlain.ateliermecaniquews.reviewssubdomain.businesslayer;

import com.champlain.ateliermecaniquews.reviewssubdomain.datalayer.Review;
import com.champlain.ateliermecaniquews.reviewssubdomain.datalayer.ReviewIdentifier;
import com.champlain.ateliermecaniquews.reviewssubdomain.datalayer.ReviewRepository;
import com.champlain.ateliermecaniquews.reviewssubdomain.datamapperlayer.ReviewRequestMapper;
import com.champlain.ateliermecaniquews.reviewssubdomain.datamapperlayer.ReviewResponseMapper;
import com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer.ReviewRequestModel;
import com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer.ReviewResponseModel;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
                LocalDateTime.parse("2024-01-02T19:00"),
                "Thank you for your service!"

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

    @Test
    void updateReview_reviewNotFound_shouldLogWarningAndReturnNull() {
        // Arrange
        String nonExistentReviewId = "nonExistentReviewId";
        ReviewRequestModel requestModel = new ReviewRequestModel(
                "customerId",
                "appointmentId",
                "This is a comment",
                5.0,
                LocalDateTime.now(),
                "Thank you for your service!"
        );

        when(reviewRepository.findReviewByReviewIdentifier_ReviewId(nonExistentReviewId)).thenReturn(null);

        // Act
        ReviewResponseModel result = reviewService.updateReview(nonExistentReviewId, requestModel);

        // Assert
        assertNull(result);
        verify(reviewRepository, times(1)).findReviewByReviewIdentifier_ReviewId(nonExistentReviewId);
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void deleteReviewByReviewId_shouldSucceed() {
        // Arrange
        String reviewId = "existingReviewId";

        // Mock the review to be deleted
        Review reviewToDelete = new Review();
        when(reviewRepository.findReviewByReviewIdentifier_ReviewId(reviewId))
                .thenReturn(reviewToDelete);

        // Act
        reviewService.deleteReviewByReviewId(reviewId);

        // Assert
        verify(reviewRepository, times(1))
                .findReviewByReviewIdentifier_ReviewId(reviewId);
        verify(reviewRepository, times(1)).delete(reviewToDelete);
    }

    @Test
    void deleteReviewByReviewId_reviewNotFound() {
        // Arrange
        String reviewId = "nonExistentReviewId";
        when(reviewRepository.findReviewByReviewIdentifier_ReviewId(reviewId))
                .thenReturn(null);

        // Act
        reviewService.deleteReviewByReviewId(reviewId);

        // Assert
        verify(reviewRepository, times(1))
                .findReviewByReviewIdentifier_ReviewId(reviewId);
        verify(reviewRepository, never()).delete(any());
    }

    @Test
    void updateMechanicReply_ReviewExists_Success() {
        // Arrange
        String reviewId = "existingReviewId";
        String mechanicReply = "Thank you for your feedback!";
        Review review = new Review(); // Assuming a constructor or builder is available
        review.setMechanicReply("Initial reply");
        Review updatedReview = new Review();
        updatedReview.setMechanicReply(mechanicReply);
        ReviewResponseModel expectedResponse = ReviewResponseModel.builder()
                .reviewId(reviewId)
                .customerId("customerId1")
                .appointmentId("appointmentId1")
                .comment("Very professional")
                .rating(4.5)
                .reviewDate(LocalDateTime.now())
                .mechanicReply("We appreciate your business!")
                .build();

        when(reviewRepository.findOptionalReviewByReviewIdentifier_ReviewId(reviewId)).thenReturn(Optional.of(review));
        when(reviewRepository.save(any(Review.class))).thenReturn(updatedReview);
        when(reviewResponseMapper.entityToResponseModel(updatedReview)).thenReturn(expectedResponse);

        // Act
        ReviewResponseModel actualResponse = reviewService.updateMechanicReply(reviewId, mechanicReply);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void updateMechanicReply_ReviewDoesNotExist_ReturnsNull() {
        // Arrange
        String reviewId = "nonExistingReviewId";
        String mechanicReply = "Thank you for your feedback!";

        when(reviewRepository.findOptionalReviewByReviewIdentifier_ReviewId(reviewId)).thenReturn(Optional.empty());

        // Act
        ReviewResponseModel actualResponse = reviewService.updateMechanicReply(reviewId, mechanicReply);

        // Assert
        assertNull(actualResponse);
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void isOwnerOfReview_UserIsOwner_ReturnsTrue() {
        // Arrange
        String authenticatedUserId = "userId1";
        String reviewId = "reviewId1";

        when(reviewRepository.findReviewByReviewIdentifier_ReviewIdAndCustomerId(reviewId, authenticatedUserId))
                .thenReturn(Optional.of(new Review())); // Assuming the presence of such a review implies ownership

        // Act
        boolean isOwner = reviewService.isOwnerOfReview(authenticatedUserId, reviewId);

        // Assert
        assertTrue(isOwner);
    }


    @Test
    void isOwnerOfReview_UserIsNotOwner_ReturnsFalse() {
        // Arrange
        String authenticatedUserId = "userId2";
        String reviewId = "reviewId1";

        when(reviewRepository.findReviewByReviewIdentifier_ReviewIdAndCustomerId(reviewId, authenticatedUserId))
                .thenReturn(Optional.empty()); // No matching review found for this user and review ID

        // Act
        boolean isOwner = reviewService.isOwnerOfReview(authenticatedUserId, reviewId);

        // Assert
        assertFalse(isOwner);
    }

    @Test
    void addReview_shouldSucceed() {
        // Arrange
        ReviewRequestModel requestModel = new ReviewRequestModel(
                "customerId1",
                "appointmentId1",
                "Great service",
                5.0,
                LocalDateTime.now(),
                null // Assuming mechanicReply is not set during review submission
        );

        Review savedReview = new Review();
        savedReview.setReviewIdentifier(new ReviewIdentifier()); // Assuming this generates a new ID
        savedReview.setCustomerId(requestModel.getCustomerId());
        savedReview.setAppointmentId(requestModel.getAppointmentId());
        savedReview.setComment(requestModel.getComment());
        savedReview.setRating(requestModel.getRating());
        savedReview.setReviewDate(requestModel.getReviewDate()); // This would actually be set in the service to LocalDateTime.now()
        savedReview.setMechanicReply(requestModel.getMechanicReply());

        ReviewResponseModel expectedResponseModel = new ReviewResponseModel(
                savedReview.getReviewIdentifier().getReviewId(),
                savedReview.getCustomerId(),
                savedReview.getAppointmentId(),
                savedReview.getComment(),
                savedReview.getRating(),
                savedReview.getReviewDate(),
                savedReview.getMechanicReply()
        );

        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);
        when(reviewResponseMapper.entityToResponseModel(any(Review.class))).thenReturn(expectedResponseModel);

        // Act
        ReviewResponseModel actualResponseModel = reviewService.addReview(requestModel);

        // Assert
        ArgumentCaptor<Review> reviewCaptor = ArgumentCaptor.forClass(Review.class);
        verify(reviewRepository).save(reviewCaptor.capture());
        Review capturedReview = reviewCaptor.getValue();

        assertEquals(requestModel.getCustomerId(), capturedReview.getCustomerId());
        assertEquals(requestModel.getAppointmentId(), capturedReview.getAppointmentId());
        assertEquals(requestModel.getComment(), capturedReview.getComment());
        assertEquals(requestModel.getRating(), capturedReview.getRating());
        // Note: Direct comparison of LocalDateTime.now() might not be accurate due to processing time
        assertNotNull(capturedReview.getReviewDate());
        assertEquals(requestModel.getMechanicReply(), capturedReview.getMechanicReply());

        assertEquals(expectedResponseModel, actualResponseModel);
    }
}







