package com.enzinior.sogo.place.repository;

import com.enzinior.sogo.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    Optional<Place> findByUuid(String placeUuid);

    List<Place> findByName(String word);

    String findByplaceInfo(Place place);


    // 장소 검색 -> query
    // 장소 찜하기
    // 내가 찜한 장소 조회
    // 장소 등록
    // 장소 수정
    // 장소 삭제

    // 장소 검증


}
