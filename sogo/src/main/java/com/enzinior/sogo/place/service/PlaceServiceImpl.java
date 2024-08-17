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
@Transactional
public class PlaceServiceImpl implements PlaceService{

    private final PlaceRepository placeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Place> searchByCon(String word) {
        return placeRepository.findByWord(word);
    }

    @Override
    @Transactional(readOnly = true)
    public String searchWhenCreateReview(Place place) {
        Place findplace = placeRepository.findByPlaceInfo(place.getPlaceName(), place.getLng(), place.getLat());
        return findplace.getPlaceUuid();
    }

    @Override
    public Place createPlace(Place place) {
        return placeRepository.save(place);
    }

    @Override
    @Transactional(readOnly = true)
    public Place getPlace(String placeUuid) {
        return verifiedByUuid(placeUuid);
    }

    @Override
    public void update(Place place, String placeUuid){
        Place findplace = verifiedByUuid(placeUuid);
        findplace.setPlaceName(place.getPlaceName());
        findplace.setPlaceDescription(place.getPlaceDescription());
        findplace.setLng(place.getLng());
        findplace.setLat(place.getLat());
    }

    @Override
    public void hide(String placeUuid){
        Place place = verifiedByUuid(placeUuid);
        if(!place.isHide()){
            place.setHide(true);
        }else{
            place.setHide(false);
        }
    }

    @Override
    public void scoreUpdate (String placeUuid, float score){
        Place place = verifiedByUuid(placeUuid);
        place.setScore(score);
    }

    private Place verifiedByUuid(String placeUuid){
        Optional<Place> optionalPlace = placeRepository.findByPlaceUuid(placeUuid);
        return optionalPlace
                .orElseThrow(() -> new RuntimeException("No Place found with uuid " + placeUuid));

    }

    // 장소 찜하기
    // 내가 찜한 장소 조회

}
