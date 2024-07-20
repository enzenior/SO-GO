package com.enzinior.sogo.place.service;

import com.enzinior.sogo.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.enzinior.sogo.place.entity.Place;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceServiceImpl implements PlaceService{

    private final PlaceRepository placeRepository;


    @Override
    public List<Place> searchByCon(String word) {
        return placeRepository.findByName(word);
    }

    @Override
    public String searchWhenCreateReview(Place place) {
        return placeRepository.findByplaceInfo(place);
    }

//    @Override
//    public String searchWhenCreateReview(Place place) {
//        return "";
//    }

    @Override
    public Place getPlace(String placeUuid) {
        Optional<Place> byUuid = placeRepository.findByUuid(placeUuid);
        return byUuid.get();
    }

    @Transactional
    @Override
    public void createPlace(Place place) {
        placeRepository.save(place);
    }

    // 장소 검색
    // 장소 상세페이지
    // 장소 찜하기
    // 내가 찜한 장소 조회
    // 장소 등록
    // 장소 수정

    // 장소 삭제
    private Place verifiedByUuid(String uuid) {
        Optional<Place> optionalPlace = placeRepository.findByUuid(uuid);
        return optionalPlace
                .orElseThrow(() -> new RuntimeException("No Place found with uuid " + uuid));
    }

}
