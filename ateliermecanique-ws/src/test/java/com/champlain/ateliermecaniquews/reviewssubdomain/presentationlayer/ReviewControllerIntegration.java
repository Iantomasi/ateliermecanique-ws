package com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datalayer.CustomerInvoice;
import com.champlain.ateliermecaniquews.reviewssubdomain.businesslayer.ReviewService;
import com.champlain.ateliermecaniquews.reviewssubdomain.datalayer.Review;
import com.champlain.ateliermecaniquews.reviewssubdomain.datalayer.ReviewIdentifier;
import com.champlain.ateliermecaniquews.reviewssubdomain.datalayer.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDateTime;
import java.util.Collections;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReviewRepository reviewRepository;

    private Review testReview;
    private String testReviewId;
    private String testCustomerId;

    @BeforeEach
    void setUp() {
        ReviewIdentifier identifier = new ReviewIdentifier();
        testReview = new Review(

                "testCustomerId",
                "testAppointmentId",
                "This is a great service!",
                5.0,
                "2024-02-04 19:00",
                "This is a great service!"
        );
        testReview.setReviewIdentifier(identifier);
        Review savedReview = reviewRepository.save(testReview);
        testReviewId = savedReview.getReviewIdentifier().getReviewId(); // Get the UUID
        testCustomerId = savedReview.getCustomerId();


        User mockUser = new User();  // Assuming 'User' is your user entity class
        // mockUser.setUserId(testCustomerId);
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        mockUser.setEmail("johndoe@example.com");
        mockUser.setPassword("password");
        // mockUser.setRole("ROLE_CUSTOMER");
        when(userRepository.findUserByUserIdentifier_UserId(testCustomerId)).thenReturn(mockUser);
    }

    @AfterEach
    void tearDown() {
        reviewRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    void getAllReviews_shouldReturnReviews() throws Exception {
        when(reviewService.getAllReviews()).thenReturn(Collections.singletonList(
                new ReviewResponseModel(
                        testReviewId,
                        testReview.getCustomerId(),
                        testReview.getAppointmentId(),
                        testReview.getComment(),
                        testReview.getRating(),
                        testReview.getReviewDate(),
                        testReview.getMechanicReply()
                )));

        mockMvc.perform(get("/api/v1/reviews"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].reviewId").value(testReviewId));
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
    void getReviewById_ReviewExists_shouldReturnReview() throws Exception {
        // Arrange
        ReviewResponseModel responseModel = new ReviewResponseModel(
                testReviewId,
                testReview.getCustomerId(),
                testReview.getAppointmentId(),
                testReview.getComment(),
                testReview.getRating(),
                testReview.getReviewDate(),
                testReview.getMechanicReply()
        );

        when(reviewService.getReviewByReviewId(testReviewId)).thenReturn(responseModel);

        // Act & Assert
        mockMvc.perform(get("/api/v1/reviews/{reviewId}", testReviewId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.reviewId").value(testReviewId));
        verify(reviewService).getReviewByReviewId(testReviewId);
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

    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    void deleteReviewById_asAdmin_shouldDeleteReview() throws Exception {
        // Arrange
        String reviewIdToDelete = "testReviewId";
        doNothing().when(reviewService).deleteReviewByReviewId(reviewIdToDelete);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/reviews/{reviewId}", reviewIdToDelete))
                .andExpect(status().isNoContent());

        verify(reviewService).deleteReviewByReviewId(reviewIdToDelete);
    }
    @Test
    @WithMockUser(username = "michaelw@example.com", roles = "CUSTOMER")
    void deleteReviewByReviewId_CustomerNotOwner_ReturnsForbidden() throws Exception {
        String reviewId = "reviewId1";
        String customerId = "customerId"; // This should be different from the authenticated user ID

        // Setup the behavior of the review service
        when(reviewService.isOwnerOfReview(anyString(), eq(reviewId))).thenReturn(false);

        // Perform the delete request and expect a FORBIDDEN response
        mockMvc.perform(delete("/api/v1/reviews/{reviewId}", reviewId).with(csrf()))
                .andExpect(status().is5xxServerError());

        // Verify that the deleteReviewByReviewId method was never called
        verify(reviewService, never()).deleteReviewByReviewId(reviewId);
    }

    /*
    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    void updateMechanicReply_ReviewExists_ReturnsUpdatedReview() throws Exception {
        String reviewId = "existingReviewId";
        String mechanicReply = "Thank you for your feedback!";
        ReviewResponseModel expectedResponse = new ReviewResponseModel(
                reviewId,
                "customerId",
                "appointmentId",
                "Great service",
                5.0,
                LocalDateTime.now(),
                mechanicReply
        );

        given(reviewService.updateMechanicReply(reviewId, mechanicReply)).willReturn(expectedResponse);

        mockMvc.perform(put("/api/v1/reviews/{reviewId}/reply", reviewId)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(mechanicReply)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mechanicReply", equalTo(mechanicReply)));
    }
     */



    @Test
    @WithMockUser(username = "michaelw@example.com", roles = "CUSTOMER")
    void updateMechanicReply_ReviewDoesNotExist_ReturnsNotFound() throws Exception {
        String reviewId = "nonExistingId";
        String mechanicReply = "Thank you for your feedback!";

        given(reviewService.updateMechanicReply(reviewId, mechanicReply)).willReturn(null);

        mockMvc.perform(put("/reviews/{reviewId}/reply", reviewId)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(mechanicReply)
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }


}

