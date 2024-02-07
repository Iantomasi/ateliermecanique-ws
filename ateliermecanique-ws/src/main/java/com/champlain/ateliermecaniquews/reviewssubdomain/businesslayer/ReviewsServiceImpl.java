package com.champlain.ateliermecaniquews.reviewssubdomain.businesslayer;


import com.champlain.ateliermecaniquews.reviewssubdomain.datalayer.ReviewsRepository;
import com.champlain.ateliermecaniquews.reviewssubdomain.datamapperlayer.ReviewResponseMapper;
import com.champlain.ateliermecaniquews.reviewssubdomain.presentationlayer.ReviewResponseModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ReviewsServiceImpl implements ReviewsService {

    private ReviewsRepository reviewsRepository;

    private ReviewResponseMapper reviewResponseMapper;

    @Override
    public List<ReviewResponseModel> getAllReviews() {
        return reviewResponseMapper.entityToReviewResponseModelList(reviewsRepository.findAll());
    }
}
