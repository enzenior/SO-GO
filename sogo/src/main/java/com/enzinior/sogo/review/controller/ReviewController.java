package com.enzinior.sogo.review.controller;

import com.enzinior.sogo.report.entity.Report;
import com.enzinior.sogo.review.dto.ReviewDto;
import com.enzinior.sogo.review.dto.ScrapDto;
import com.enzinior.sogo.review.entity.Review;
import com.enzinior.sogo.review.mapper.ReviewMapper;
import com.enzinior.sogo.review.service.ReviewService;
import com.enzinior.sogo.review.service.ScrapService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Validated
public class ReviewController {
    private final ReviewService reviewService;
    private final ScrapService scrapService;
    private final ReviewMapper mapper;

    @PostMapping
    public ResponseEntity postReview(@Valid @RequestBody ReviewDto.Post requestBody) {
        Review review = mapper.reviewPostToReview(requestBody);
        Review createdReview = reviewService.createReview(review, requestBody.getAddress());

        return ResponseEntity.ok(createdReview.getReviewUuid());
    }

    @PostMapping("/hide/{review-uuid}")
    public ResponseEntity hideReview(@PathVariable("review-uuid") String reviewUuid) {
        Review review = reviewService.hideReview(reviewUuid);
        return ResponseEntity.ok(review);
    }

    @PatchMapping("/{review-uuid}")
    public ResponseEntity patchReview(@Valid @RequestBody ReviewDto.Patch requestBody, @PathVariable("review-uuid") String reviewUuid) {
        requestBody.setReviewUuid(reviewUuid);
        Review review = mapper.reviewPatchToReview(requestBody);
        Review updatedReview = reviewService.updateReview(review);
        return ResponseEntity.ok(mapper.reviewToReviewResponseDto(updatedReview));
    }

    @DeleteMapping("/{review-uuid}")
    public ResponseEntity deleteReview(@PathVariable("review-uuid") String reviewUuid) {
        reviewService.deleteReview(reviewUuid);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{review-uuid}")
    public ResponseEntity scrapReview(@PathVariable("review-uuid") String reviewUuid, @RequestBody ScrapDto requestBody) {
        scrapService.createScrap(reviewUuid, requestBody.getUserUuid());
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(mapper.reviewsToReviewResponseDtos(reviews));
    }

    @GetMapping("/place/{place-uuid}")
    public ResponseEntity getPlaceReviews(@PathVariable("place-uuid") String placeUuid) {
        List<Review> reviews = reviewService.getPlaceReviews(placeUuid);
        return ResponseEntity.ok(mapper.reviewsToReviewResponseDtos(reviews));
    }

    @GetMapping("/{review-uuid}")
    public ResponseEntity getReviewDetail(@PathVariable("review-uuid") String reviewUuid) {
        return ResponseEntity.ok(mapper.reviewToReviewResponseDto(reviewService.getReview(reviewUuid)));
    }

    // Report 신고
    @PostMapping("/reports")
    public ResponseEntity postReport(@Valid @RequestBody ReviewDto.Report requestBody) {
        Report report = reviewService.createReport(requestBody);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/my-reviews/{user-uuid}")
    public ResponseEntity getUserReviews(@PathVariable("user-uuid") String userUuid) {
        List<Review> reviews = reviewService.getUserReviews(userUuid);
        return ResponseEntity.ok(mapper.reviewsToReviewResponseDtos(reviews));
    }

    @GetMapping("/scraps/{user-uuid}")
    public ResponseEntity getScrapReviews(@PathVariable("user-uuid") String userUuid) {
        List<Review> reviews = reviewService.getScrapReviews(userUuid);
        return ResponseEntity.ok(mapper.reviewsToReviewResponseDtos(reviews));
    }
}
