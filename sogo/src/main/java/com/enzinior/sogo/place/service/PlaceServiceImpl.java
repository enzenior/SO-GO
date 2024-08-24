package com.enzinior.sogo.place.service;

import com.enzinior.sogo.exception.BusinessLogicException;
import com.enzinior.sogo.exception.ExceptionCode;
import com.enzinior.sogo.openai.service.SummaryService;
import com.enzinior.sogo.place.dto.PlaceDto;
import com.enzinior.sogo.place.entity.Heart;
import com.enzinior.sogo.place.repository.HeartRepository;
import com.enzinior.sogo.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.enzinior.sogo.place.entity.Place;
import com.enzinior.sogo.user.entity.User;
import com.enzinior.sogo.user.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaceServiceImpl implements PlaceService{

    private final PlaceRepository placeRepository;
    private final SummaryService summaryService;
    private final UserRepository userRepository;
    private final HeartRepository heartRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Place> searchByCon(String word) {
        return placeRepository.findByWord(word);
    }

    @Override
    @Transactional(readOnly = true)
    public String searchWhenCreateReview(Place place) {
        Place findplace = placeRepository.findByPlaceInfo(place.getPlaceName(), place.getLng(), place.getLat());
        if(findplace==null){
            findplace = createPlace(place);
        }
        return findplace.getPlaceUuid();
    }

    @Override
    public Place createPlace(Place place) {
        String description = place.getPlaceDescription();
        String summary = summaryService.generateSummary(description);
        String[] summaryArray = summary.split("\n");
        place.setSummary(summaryArray[0]);
        place.setTag(summaryArray[1]);
        return placeRepository.save(place);
    }

    @Override
    @Transactional(readOnly = true)
    public Place getPlace(String placeUuid) {
        Place place = verifiedByUuid(placeUuid);
        return place;
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

    @Override
    public boolean updateHeart(String placeUuid, String userUuid) {
        Place place = verifiedByUuid(placeUuid);
        User user = userRepository.findByUserUuid(userUuid)
            .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        Heart heart = heartRepository.findHeartByPlaceAndUser(placeUuid, userUuid).get();
        boolean result = false;
        if(heart == null){
            heart = new Heart();
            heart.setPlace(place);
            heart.setUser(user);
            heartRepository.save(heart);
            int cnt = place.getHeartCnt();
            place.setHeartCnt(cnt+1);
            result = true;
        }else{
            heartRepository.delete(heart);
            int cnt = place.getHeartCnt();
            place.setHeartCnt(cnt-1);
            result = false;
        }
        return result;
    }

    @Override
    public List<Place> getMyPlaces(String userUuid) {
        User user = userRepository.findByUserUuid(userUuid)
            .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        List<Place> places = heartRepository.findPlacesByUser(userUuid);
        return places;
    }

    private Place verifiedByUuid(String placeUuid){
        Optional<Place> optionalPlace = placeRepository.findByPlaceUuid(placeUuid);
        return optionalPlace
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PLACE_NOT_FOUND));

    }


    // 장소 찜하기
    // 내가 찜한 장소 조회

}
