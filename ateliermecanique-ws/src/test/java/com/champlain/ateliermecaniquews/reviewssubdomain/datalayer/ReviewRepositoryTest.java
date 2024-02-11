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

}
