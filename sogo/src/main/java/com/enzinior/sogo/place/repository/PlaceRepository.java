package com.enzinior.sogo.place.repository;

import com.enzinior.sogo.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    Optional<Place> findByPlaceUuid(String placeUuid);

    @Query("SELECT p FROM Place p WHERE p.placeName LIKE CONCAT('%',:word, '%') OR p.placeDescription LIKE CONCAT('%',:word, '%') ")
    List<Place> findByWord(String word);

    @Query("SELECT p FROM Place p WHERE p.placeName = :name AND p.lng = :lng AND p.lat = :lat")
    Place findByPlaceInfo(String name, double lng, double lat);


    // 장소 찜하기
    // 내가 찜한 장소 조회
    // 장소 등록
    // 장소 수정
    // 장소 삭제

    // 장소 검증


}
