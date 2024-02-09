package com.champlain.ateliermecaniquews.reviewssubdomain.businesslayer;

import com.champlain.ateliermecaniquews.reviewssubdomain.datalayer.Reviews;
import com.champlain.ateliermecaniquews.reviewssubdomain.datalayer.ReviewsRepository;
import com.champlain.ateliermecaniquews.reviewssubdomain.datamapperlayer.ReviewResponseMapper;
import com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer.ReviewResponseModel;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;


@SpringBootTest
class ReviewsServiceImplTest {

    @Mock
    private ReviewsRepository reviewsRepository;

    @Mock
    private ReviewResponseMapper reviewResponseMapper;

    @InjectMocks
    private ReviewsServiceImpl reviewsService;


    @Test
    void getAllReviews_shouldSucceed() {

        // Arrange
        Reviews review = new Reviews(
                "John Doe",
                "I made a review test",
                4.0
        );

        List<Reviews> reviews = Collections.singletonList(review);
        when(reviewsRepository.findAll()).thenReturn(reviews);

        ReviewResponseModel responseModel = ReviewResponseModel.builder()
                .reviewId(review.getReviewIdentifier().getReviewId().toString())
                .customer_name(review.getCustomer_name())
                .comment(review.getComment())
                .rating(review.getRating())
                .build();

        List<ReviewResponseModel> responseModels = Collections.singletonList(responseModel);

        when(reviewResponseMapper.entityToReviewResponseModelList(reviews)).thenReturn(responseModels);

        // Act
        List<ReviewResponseModel> result = reviewsService.getAllReviews();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(responseModels.size(), result.size());
        assertEquals(responseModels.get(0).getReviewId(), result.get(0).getReviewId());

        verify(reviewsRepository, times(1)).findAll();
        verify(reviewResponseMapper, times(1)).entityToReviewResponseModelList(reviews);

    }


}