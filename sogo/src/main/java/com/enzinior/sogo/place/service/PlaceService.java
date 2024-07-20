package com.enzinior.sogo.place.service;

import com.enzinior.sogo.place.dto.PlaceDto;
import com.enzinior.sogo.place.entity.Place;

import java.util.List;

public interface PlaceService {

    // 장소 검색
    List<Place> searchByCon(String word);

    // 리뷰 등록시 장소 검색 /search
    String searchWhenCreateReview(Place place);

    // 장소 등록
    Place createPlace(Place place);

    // 장소 상세페이지
    Place getPlace(String placeUuid);



    // 장소 찜하기
    // 내가 찜한 장소 조회
    // 장소 등록
    // 장소 수정
    // 장소 삭제

}
