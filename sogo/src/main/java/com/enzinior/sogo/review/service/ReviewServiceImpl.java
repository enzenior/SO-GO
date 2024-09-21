package com.enzinior.sogo.review.service;

import com.enzinior.sogo.notification.service.NotificationService;
import com.enzinior.sogo.place.entity.Place;
import com.enzinior.sogo.place.service.PlaceService;
import com.enzinior.sogo.report.entity.Report;
import com.enzinior.sogo.report.service.ReportService;
import com.enzinior.sogo.review.dto.ReviewDto;
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
    private final NotificationService notificationService;
    private final CustomBeanUtils<Review> beanUtils;
    private final ReportService reportService;

    @Transactional
    @Override
    public Review createReview(Review review, String address) {
        String placeUuid = review.getPlace().getPlaceUuid();
        Place place = placeService.getPlace(placeUuid);
        updatePlaceScore(review.getScore(), placeUuid, place);

        User user = userService.findUser(review.getUser().getUserUuid());
        userService.updateMaps(user, address);
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

    @Transactional
    @Override
    public Report createReport(ReviewDto.Report requestBody) {
        Review review = verifiedByUuid(requestBody.getReviewUuid());
        User user = userService.findUser(requestBody.getUserUuid());
        Report report = new Report();
        report.setContent(requestBody.getContent());
        report.setTargetId(review.getReviewId());
        report.setUser(user);
        report.setReportType(0);
        return reportService.postReport(report);
    }

    @Override
    public List<Review> getUserReviews(String userUuid) {
        return reviewRepository.findByUserUserUuid(userUuid);
    }

    @Override
    public List<Review> getScrapReviews(String userUuid) {
        return reviewRepository.findScraped(userUuid);
    }

    @Transactional
    @Override
    public Review hideReview(String reviewUuid) {
        Review review = verifiedByUuid(reviewUuid);
        review.setSecret(!review.getSecret());
        return review;
    }

    @Transactional
    @Override
    public void updateMaxCnt(User user, Review review, Integer count) {
        if (review.getMaxCnt() < count) {
            review.setMaxCnt(review.getMaxCnt() + 1);
            int maxCnt = review.getMaxCnt();

            if (maxCnt % 100 == 0) {
                StringBuilder content = new StringBuilder();
                content.append("🎉  ").append(user.getNickname()).append("님의 글이 스크랩 ").append(maxCnt).append("개를 돌파했습니다!");
                notificationService.createNotificationByReview(user, content.toString(), review);
            }
        }
    }



    private Review verifiedByUuid(String uuid) {
        Optional<Review> optionalReview = reviewRepository.findByReviewUuid(uuid);
        return optionalReview
            .orElseThrow(() -> new RuntimeException("No Review found with uuid " + uuid));
    }
}
