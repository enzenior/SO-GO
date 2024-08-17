package com.enzinior.sogo.review.service;

import com.enzinior.sogo.review.dto.ReviewDto;
import com.enzinior.sogo.review.entity.Review;

import java.util.List;

public interface ReviewService {
    Review createReview(Review review);
    Review updateReview(Review review);
    List<Review> getAllReviews();
    List<Review> getPlaceReviews(String placeUuid);
    Review getReview(String reviewUuid);
    void deleteReview(String reviewUuid);
//    Report createReport(ReviewDto.Report requestBody);
    List<Review> getUserReviews(String userUuid);

    List<Review> getScrapReviews(String userUuid);
}
