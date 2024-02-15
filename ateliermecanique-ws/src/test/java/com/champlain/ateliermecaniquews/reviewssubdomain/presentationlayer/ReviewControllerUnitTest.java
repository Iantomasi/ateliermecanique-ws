package com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.UserIdentifier;
import com.champlain.ateliermecaniquews.authenticationsubdomain.utils.security.services.UserDetailsImpl;
import com.champlain.ateliermecaniquews.reviewssubdomain.businesslayer.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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

    @Test
    void updateReview_reviewNotFound_shouldReturnNotFound() {
        testReviewRequest = ReviewRequestModel.builder()
                .customerId("testCustomerId")
                .appointmentId("testAppointmentId")
                .comment("Updated service comment")
                .rating(4.0)
                .reviewDate(LocalDateTime.now())
                .build();

        when(reviewService.updateReview("nonexistentReviewId", testReviewRequest)).thenReturn(null);

        ResponseEntity<ReviewResponseModel> response = reviewController.updateReview("nonexistentReviewId", testReviewRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void deleteReviewById_CustomerRole_Unauthorized() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                .when(authentication).getAuthorities();

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(1, new UserIdentifier().getUserId(), "John", "Vegas", "444", "john@email.com", "pass", authorities);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(reviewService.isOwnerOfReview(anyString(), anyString())).thenReturn(false);

        // Act
        ResponseEntity<Void> response = reviewController.deleteReviewById("testReviewId");

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void addReview_failure_shouldReturnBadRequest() {
        // Arrange
        ReviewRequestModel requestModel = new ReviewRequestModel(
                "testCustomerId",
                "testAppointmentId",
                "Poor service", // Assume this fails validation
                2.0,
                LocalDateTime.now(),
                null
        );

        when(reviewService.addReview(requestModel)).thenReturn(null); // Simulate failure

        // Act
        ResponseEntity<ReviewResponseModel> response = reviewController.addReview(requestModel);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody()); // Expect no body for a BAD_REQUEST

        verify(reviewService, times(1)).addReview(requestModel);
    }







}