package com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer;


import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import com.champlain.ateliermecaniquews.reviewssubdomain.businesslayer.ReviewsService;
import com.champlain.ateliermecaniquews.reviewssubdomain.datalayer.ReviewIdentifier;
import com.champlain.ateliermecaniquews.reviewssubdomain.datalayer.Reviews;
import com.champlain.ateliermecaniquews.reviewssubdomain.datalayer.ReviewsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerIntegration {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewsService reviewsService;


    @MockBean
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReviewsRepository reviewsRepository;

    private Reviews testReview;
    private String _testReviewId;
    private String _test_customer_name;
    private String _test_comment;
    private Double _test_rating;

    @BeforeEach
    public void setUp()
    {
        ReviewIdentifier reviewIdentifier = new ReviewIdentifier();
        testReview = new Reviews(
            "John Doe",
            "I made a review test",
            4.0
        );

        testReview.setReviewIdentifier(reviewIdentifier);
        Reviews savedReview = reviewsRepository.save(testReview);
        _testReviewId = savedReview.getReviewIdentifier().getReviewId().toString();

        _test_customer_name = savedReview.getCustomer_name();
        _test_comment = savedReview.getComment();
        _test_rating = savedReview.getRating();

//        User mockUser = new User();  // Assuming 'User' is your user entity class
//        // mockUser.setUserId(testCustomerId);
//        mockUser.setFirstName("John");
//        mockUser.setLastName("Doe");
//        mockUser.setEmail("johndoe@example.com");
//        mockUser.setPassword("password");
//        // mockUser.setRole("ROLE_CUSTOMER");
//        when(userRepository.findUserByUserIdentifier_UserId(testCustomerId)).thenReturn(mockUser);


    }

    @AfterEach
    void tearDown() {
        reviewsRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    void testGetAllReviews() throws Exception {
        when(reviewsService.getAllReviews()).thenReturn(Collections.singletonList(
            new ReviewResponseModel(_testReviewId, _test_customer_name, _test_comment, _test_rating)));


        mockMvc.perform(get("/api/v1/reviews"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].reviewId").value(_testReviewId))
                .andExpect(jsonPath("$[0].customer_name").value(_test_customer_name))
                .andExpect(jsonPath("$[0].comment").value(_test_comment))
                .andExpect(jsonPath("$[0].rating").value(_test_rating));



    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    void testGetAllReviewsWhenNoReviewsFound() throws Exception {

        when(reviewsService.getAllReviews()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/reviews"))
                .andExpect(status().isNotFound());
    }

}


