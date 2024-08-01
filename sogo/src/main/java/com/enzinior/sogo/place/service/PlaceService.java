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

    // 장소 수정
    void update(Place place, String placeUuid);

    // 장소 숨김
    void hide(String placeUuid);

    // 장소 점수 수정
    void scoreUpdate (String placeUuid, float score);


    // 장소 찜하기
    // 내가 찜한 장소 조회

}
