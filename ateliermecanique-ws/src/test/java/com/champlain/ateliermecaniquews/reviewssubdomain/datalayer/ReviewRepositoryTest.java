package com.champlain.ateliermecaniquews.reviewssubdomain.datalayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Appointment;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.AppointmentIdentifier;
import com.champlain.ateliermecaniquews.reviewssubdomain.datamapperlayer.ReviewRequestMapper;
import com.champlain.ateliermecaniquews.reviewssubdomain.datamapperlayer.ReviewResponseMapper;
import com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer.ReviewRequestModel;
import com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer.ReviewResponseModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    private final ReviewRequestMapper requestMapper = Mappers.getMapper(ReviewRequestMapper.class);
    private final ReviewResponseMapper responseMapper = Mappers.getMapper(ReviewResponseMapper.class);

    private String savedReviewId;
    private String savedCustomerId;



    @BeforeEach
    void setUp() {
        Review newReview = new Review();
        ReviewIdentifier identifier = new ReviewIdentifier();
        newReview.setReviewIdentifier(identifier);
        newReview.setCustomerId("testCustomerId"); // Set a test customer id
        Review savedReview = reviewRepository.save(newReview);
        savedReviewId = savedReview.getReviewIdentifier().getReviewId();
        savedCustomerId = savedReview.getCustomerId();
    }

    @AfterEach
    void tearDown() {
        reviewRepository.deleteAll();
    }

    @Test
    void testRequestModelToEntityMapping() {
        ReviewRequestModel requestModel = ReviewRequestModel.builder()
                .customerId("testCustomerId")
                .appointmentId("testAppointmentId")
                .comment("Excellent service")
                .rating(5.0)
                .reviewDate(LocalDateTime.now())
                .build();

        Review review = requestMapper.requestModelToEntity(requestModel);

        assertNotNull(review);
        assertEquals(requestModel.getCustomerId(), review.getCustomerId());
        assertEquals(requestModel.getRating(), review.getRating());
        assertEquals(requestModel.getComment(), review.getComment());
    }

    @Test
    void testEntityToResponseModelMapping() {
        // Prepare a review entity directly
        Review review = new Review();
        review.setCustomerId("testCustomerId");
        review.setAppointmentId("testAppointmentId");
        review.setComment("Excellent service");
        review.setRating(5.0);
        review.setReviewDate(LocalDateTime.now());

        Review savedReview = reviewRepository.save(review);

        ReviewResponseModel responseModel = responseMapper.entityToResponseModel(savedReview);

        assertEquals(savedReview.getCustomerId(), responseModel.getCustomerId());
        assertEquals(savedReview.getRating(), responseModel.getRating());
        assertEquals(savedReview.getComment(), responseModel.getComment());
        // assertEquals(savedReview.getReviewIdentifier().getReviewId(), responseModel.getReviewId());
    }

    @Test
    void entityToResponseModelList_ConvertsListCorrectly() {
        // Given
        Review review1 = new Review();
        review1.setCustomerId("customer1");
        review1.setAppointmentId("appointment1");
        review1.setComment("Great service");
        review1.setRating(5.0);
        review1.setReviewDate(LocalDateTime.now());
        review1.setReviewIdentifier(new ReviewIdentifier());

        Review review2 = new Review();
        review2.setCustomerId("customer2");
        review2.setAppointmentId("appointment2");
        review2.setComment("Excellent service");
        review2.setRating(4.5);
        review2.setReviewDate(LocalDateTime.now());
        review2.setReviewIdentifier(new ReviewIdentifier());

        reviewRepository.save(review1);
        reviewRepository.save(review2);

        List<Review> savedReviews = reviewRepository.findAll();

        // When
        List<ReviewResponseModel> responseModels = responseMapper.entityToResponseModelList(savedReviews);

        // Then
        assertNotNull(responseModels);
        assertEquals(3, responseModels.size());

        ReviewResponseModel response1 = responseModels.get(1);
        assertEquals(review1.getCustomerId(), response1.getCustomerId());
        assertEquals(review1.getComment(), response1.getComment());
        assertEquals(review1.getRating(), response1.getRating());

        ReviewResponseModel response2 = responseModels.get(2);
        assertEquals(review2.getCustomerId(), response2.getCustomerId());
        assertEquals(review2.getComment(), response2.getComment());
        assertEquals(review2.getRating(), response2.getRating());
    }


    @Test
    public void findReviewByReviewId_shouldSucceed() {
        // Arrange
        assertNotNull(savedReviewId);

        // Act
        Review found = reviewRepository.findReviewByReviewIdentifier_ReviewId(savedReviewId);

        // Assert
        assertNotNull(found);
        assertEquals(savedReviewId, found.getReviewIdentifier().getReviewId());
    }


    @Test
    void findReviewByReviewId_nonExistentId_shouldReturnNull() {
        // Arrange
        String nonExistentReviewId = "nonexistent";

        // Act
        Review foundReview = reviewRepository.findReviewByReviewIdentifier_ReviewId(nonExistentReviewId);

        // Assert
        assertNull(foundReview);
    }

    @Test
    void findReviewByReviewIdentifier_ReviewIdAndCustomerId_ExistingEntities_ShouldReturnReview() {
        // Arrange
        Review newReview = new Review();
        ReviewIdentifier identifier = new ReviewIdentifier();
        newReview.setReviewIdentifier(identifier);
        newReview.setCustomerId(savedCustomerId); // Use the savedCustomerId from setUp
        reviewRepository.save(newReview);

        // Act
        Optional<Review> foundReviewOpt = reviewRepository.findReviewByReviewIdentifier_ReviewIdAndCustomerId(savedReviewId, savedCustomerId);

        // Assert
        assertTrue(foundReviewOpt.isPresent(), "Review should be found with existing reviewId and customerId");
        foundReviewOpt.ifPresent(foundReview -> {
            assertEquals(savedReviewId, foundReview.getReviewIdentifier().getReviewId(), "Review ID should match");
            assertEquals(savedCustomerId, foundReview.getCustomerId(), "Customer ID should match");
        });
    }


    @Test
    void findReviewByReviewIdentifier_ReviewIdAndCustomerId_NonExistingEntities_ShouldReturnEmptyOptional() {
        // Arrange
        String nonExistentReviewId = "nonExistentReviewId";
        String nonExistentCustomerId = "nonExistentCustomerId";

        // Act
        Optional<Review> foundReviewOpt = reviewRepository.findReviewByReviewIdentifier_ReviewIdAndCustomerId(nonExistentReviewId, nonExistentCustomerId);

        // Assert
        assertFalse(foundReviewOpt.isPresent(), "Review should not be found with non-existing reviewId and customerId");
    }


}
