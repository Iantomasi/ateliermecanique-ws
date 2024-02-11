package com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.reviewssubdomain.businesslayer.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    private ReviewResponseModel testReview;

    @BeforeEach
    void setUp() {
        // Setup a test review to use in the tests
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
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    void getAllReviews_shouldReturnReviews() throws Exception {
        // Arrange
        when(reviewService.getAllReviews()).thenReturn(List.of(testReview));

        // Act & Assert
        mockMvc.perform(get("/api/v1/reviews"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].reviewId").value(testReview.getReviewId()));

        verify(reviewService).getAllReviews();
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    void getAllReviews_noReviewsFound_shouldReturnNotFound() throws Exception {
        // Arrange
        when(reviewService.getAllReviews()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/v1/reviews"))
                .andExpect(status().isNotFound());

        verify(reviewService).getAllReviews();
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    void getReviewById_shouldReturnReview() throws Exception {
        // Arrange
        when(reviewService.getReviewByReviewId(testReview.getReviewId())).thenReturn(testReview);

        // Act & Assert
        mockMvc.perform(get("/api/v1/reviews/{reviewId}", testReview.getReviewId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.reviewId").value(testReview.getReviewId()));

        verify(reviewService).getReviewByReviewId(testReview.getReviewId());
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    void getReviewById_notFound_shouldReturnNotFound() throws Exception {
        // Arrange
        when(reviewService.getReviewByReviewId("nonexistentReviewId")).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/v1/reviews/{reviewId}", "nonexistentReviewId"))
                .andExpect(status().isNotFound());

        verify(reviewService).getReviewByReviewId("nonexistentReviewId");
    }
}
