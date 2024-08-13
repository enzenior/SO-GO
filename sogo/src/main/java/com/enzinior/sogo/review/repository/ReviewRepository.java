package com.enzinior.sogo.review.repository;

import com.enzinior.sogo.review.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByReviewUuid(String uuid);

    @EntityGraph(attributePaths = {"place", "user"})
    List<Review> findByPlacePlaceUuid(String placeUuid);
    @EntityGraph(attributePaths = {"user"})
    List<Review> findByUserUserUuid(String userUuid);
    @Query("SELECT distinct r FROM Review r JOIN r.scraps s JOIN s.user u " +
        "WHERE u.userUuid = :userUuid")
    List<Review> findScraped(String userUuid);
}
