package com.enzinior.sogo.user.repository;

import com.enzinior.sogo.user.entity.Maps;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MapsRepository extends JpaRepository<Maps, Integer> {
    Optional<Maps> findByAddress(String address);

    List<Maps> findAllByUserUserUuid(String userUuid);
    void deleteAllByUserUserUuid(String userUuid);
    void deleteAllByReviewReviewUuid(String reviewUuid);
}
