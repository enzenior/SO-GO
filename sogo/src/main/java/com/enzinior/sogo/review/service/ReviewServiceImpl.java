package com.enzinior.sogo.review.service;

import com.enzinior.sogo.review.entity.Review;
import com.enzinior.sogo.review.repository.ReviewRepository;
import com.enzinior.sogo.utils.CustomBeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;
    private final CustomBeanUtils<Review> beanUtils;

    @Override
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(Review review) {
        Review findReview = verifiedByUuid(review.getReviewUuid());
        return beanUtils.copyNonNullProperties(review, findReview);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> getPlaceReviews(String placeUuid) {
        return reviewRepository.findByPlacePlaceUuid(placeUuid);
    }

    @Override
    public Review getReview(String reviewUuid) {
        return verifiedByUuid(reviewUuid);
    }

    @Override
    public void deleteReview(String reviewUuid) {
        reviewRepository.delete(verifiedByUuid(reviewUuid));
    }

//    @Override
//    public Report createReport(ReviewDto.Report requestBody) {
//        return null;
//    }

    @Override
    public List<Review> getUserReviews(String userUuid) {
        return reviewRepository.findByUserUserUuid(userUuid);
    }

    @Override
    public List<Review> getScrapReviews(String userUuid) {
        return reviewRepository.findScraped(userUuid);
    }

    private Review verifiedByUuid(String uuid) {
        Optional<Review> optionalReview = reviewRepository.findByReviewUuid(uuid);
        return optionalReview
            .orElseThrow(() -> new RuntimeException("No Review found with uuid " + uuid));
    }
}
