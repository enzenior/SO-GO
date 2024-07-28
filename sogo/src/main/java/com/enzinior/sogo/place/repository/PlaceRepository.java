package com.enzinior.sogo.place.repository;

import com.enzinior.sogo.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    Optional<Place> findByUuid(String placeUuid);

    @Query("SELECT p FROM Place p WHERE p.placeName CONCAT('%',:word, '%') OR p.placeDescription CONCAT('%',:word, '%') ")
    List<Place> findByWord(String word);

    @Query("SELECT p FROM Place p WHERE p.placeName = :name AND p.lng = :lng AND p.lat = p.lat")
    String findByplaceInfo(String name, double lng, double lat);


    // 장소 검색 -> query
    // 장소 찜하기
    // 내가 찜한 장소 조회
    // 장소 등록
    // 장소 수정
    // 장소 삭제

    // 장소 검증


}
