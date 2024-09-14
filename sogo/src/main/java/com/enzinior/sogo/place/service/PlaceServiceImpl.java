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
import com.enzinior.sogo.report.entity.Report;
import com.enzinior.sogo.report.service.ReportService;
import com.enzinior.sogo.user.entity.User;
import com.enzinior.sogo.user.repository.UserRepository;
import com.enzinior.sogo.user.service.UserService;

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
    private final UserService userService;
    private final HeartRepository heartRepository;
    private final ReportService reportService;

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
        String description = "장소 이름 : "+ place.getPlaceName() + "\n" + "장소 상세 주소" + place.getAddress();
        String summary = summaryService.generateSummary(description);
        String[] summaryArray = summary.split("\n");
        place.setSummary(summaryArray[0].trim()); //
        place.setTag(summaryArray[1].trim()); //
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
        User user = userService.findUser(userUuid);
        Heart heart = findHeart(placeUuid, userUuid);
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
    public Heart findHeart(String placeUuid, String userUuid) {
        Optional<Heart> optionalHeart = heartRepository.findHeartByPlaceAndUser(placeUuid, userUuid);
        return optionalHeart
            .orElse(null);
    }

    @Override
    public List<Place> getMyPlaces(String userUuid) {
        User user = userService.findUser(userUuid);
        List<Place> places = heartRepository.findPlacesByUser(userUuid);
        return places;
    }

    @Override
    public Report reportPlace(String placeUuid, String userUuid, String content) {
        User user = userService.findUser(userUuid);
        Place place = verifiedByUuid(placeUuid);
        Report report = new Report();
        report.setUser(user);
        report.setTargetId(place.getPlaceId());
        report.setReportType(2);
        report.setContent(content);

        return reportService.postReport(report);
    }

    private Place verifiedByUuid(String placeUuid){
        Optional<Place> optionalPlace = placeRepository.findByPlaceUuid(placeUuid);
        return optionalPlace
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PLACE_NOT_FOUND));
    }

}
