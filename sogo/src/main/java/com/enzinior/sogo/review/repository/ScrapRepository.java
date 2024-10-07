package com.enzinior.sogo.review.repository;

import com.enzinior.sogo.review.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findByReviewReviewIdAndUserUserId(Long reviewId, Long userId);
    Integer countByReviewReviewId(Long reviewId);
    Boolean existsByReviewReviewUuidAndUserUserUuid(String reviewUuid, String userUuid);
    void deleteAllByReviewReviewId(Long reviewId);
}
