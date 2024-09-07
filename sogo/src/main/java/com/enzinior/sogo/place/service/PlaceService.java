package com.enzinior.sogo.place.service;

import com.enzinior.sogo.place.dto.PlaceDto;
import com.enzinior.sogo.place.entity.Heart;
import com.enzinior.sogo.place.entity.Place;
import com.enzinior.sogo.report.entity.Report;

import java.util.List;

public interface PlaceService {

    // 장소 검색
    List<Place> searchByCon(String word);

    // 리뷰 등록시 장소 검색
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

    // 장소 찜하기/찜풀기
    boolean updateHeart (String placeUuid, String userUuid);

    // 찜한장소 유무 확인하지
    Heart findHeart (String placeUuid, String userUuid);

    // 내가 찜한 장소 조회
    List<Place> getMyPlaces(String userUuid);

    // 장소 신고
    Report reportPlace(String placeUuid, String userUuid, String content);
}
