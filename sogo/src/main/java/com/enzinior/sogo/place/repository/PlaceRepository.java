package com.enzinior.sogo.place.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.enzinior.sogo.place.entity.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    Optional<Place> findByPlaceUuid(String placeUuid);

    @Query("SELECT p FROM Place p WHERE p.placeName LIKE CONCAT('%',:word, '%') OR p.address LIKE CONCAT('%',:word, '%') ORDER BY p.heartCnt DESC ")
    List<Place> findByWord(String word);

    @Query("SELECT p FROM Place p WHERE p.placeNoEmptyName = :name AND TRUNCATE(p.lng, 3) = truncate (:lng, 3) AND TRUNCATE(p.lat, 3) = TRUNCATE(:lat, 3)")
    Place findByPlaceInfo(String name, double lng, double lat);

}
