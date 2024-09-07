package com.enzinior.sogo.review.service;

import com.enzinior.sogo.place.entity.Place;
import com.enzinior.sogo.place.service.PlaceService;
import com.enzinior.sogo.review.entity.Review;
import com.enzinior.sogo.review.repository.ReviewRepository;
import com.enzinior.sogo.user.entity.User;
import com.enzinior.sogo.user.service.UserService;
import com.enzinior.sogo.utils.CustomBeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final PlaceService placeService;
    private final CustomBeanUtils<Review> beanUtils;

    @Transactional
    @Override
    public Review createReview(Review review) {
        String placeUuid = review.getPlace().getPlaceUuid();
        Place place = placeService.getPlace(placeUuid);
        updatePlaceScore(review.getScore(), placeUuid, place);

        User user = userService.findUser(review.getUser().getUserUuid());
        review.setUser(user);
        review.setPlace(place);
        return reviewRepository.save(review);
    }

    private void updatePlaceScore(int reviewScore, String placeUuid, Place place) {
        int counts = reviewRepository.findByPlacePlaceUuid(placeUuid).size();
        place.setScore(
            ((place.getScore() * counts) + (float) reviewScore) / (counts + 1));
    }

    @Transactional
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

    @Transactional
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
