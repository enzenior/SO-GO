package com.enzinior.sogo.place.contoller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enzinior.sogo.place.dto.PlaceDto;
import com.enzinior.sogo.place.entity.Heart;
import com.enzinior.sogo.place.entity.Place;
import com.enzinior.sogo.place.mapper.PlaceMapper;
import com.enzinior.sogo.place.service.PlaceService;
import com.enzinior.sogo.utils.UriCreator;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("/api/places")
public class PlaceController{

    private final PlaceService placeService;
    private final PlaceMapper placeMapper;

    // 장소 검색
    @GetMapping
    @Operation(summary = "장소 검색") // 기본적으로 간단한 정보들을 가져갈 때
    public ResponseEntity search(@Valid @RequestParam String word){
        List<Place> placeList = placeService.searchByCon(word);
        List<PlaceDto.SimpleResponse> simpleResponses = placeMapper.placesToPlaceDtoSimpleResponses(placeList);
        return ResponseEntity.ok(simpleResponses);
    }

    //리뷰 생성시 장소 uuid 검색
    @PostMapping("/search")
    @Operation(summary = "리뷰 생성시 장소 uuid 검색") // service
    public ResponseEntity whenCreateReview(@Valid @RequestBody PlaceDto.Post requestBody){
        Place place = placeMapper.placePostToPlace(requestBody);
        PlaceDto.ReviewUuidDto reviewPlaceUuid = placeMapper.placeToReviewUuidDto(placeService.searchWhenCreateReview(place));
        return ResponseEntity.ok(reviewPlaceUuid);
    }

    // 장소 등록
    @PostMapping
    @Operation(summary = "장소 등록")
    public ResponseEntity postPlace(@Valid @RequestBody PlaceDto.Post requestBody) {
        Place place = placeMapper.placePostToPlace(requestBody);
        Place createPlace = placeService.createPlace(place);

        URI location = UriCreator.createUri("/places", createPlace.getPlaceId());
        return ResponseEntity.created(location).build();
    }

    // 장소 상세 페이지
    @GetMapping("/{place-uuid}")
    @Operation(summary = "장소 상세페이지")
    public ResponseEntity getPlaceDetail(@PathVariable("place-uuid") String placeUuid, @RequestParam("userUuid") String userUuid){
        PlaceDto.Response placeRes = placeMapper.placeToPlaceDtoResponse(placeService.getPlace(placeUuid));
        Heart heart = placeService.findHeart(placeUuid, userUuid);
        if(heart != null){
            placeRes.setUserHeart(true);
        }
        return ResponseEntity.ok(placeRes);
    }

    // 장소 수정  //
    @PatchMapping("/{place-uuid}")
    @Operation(summary = "장소 수정")
    public ResponseEntity updatePlace(@Valid @RequestBody PlaceDto.Post requestBody, @PathVariable("place-uuid") String placeUuid){
        Place place = placeMapper.placePostToPlace(requestBody);
        placeService.update(place, placeUuid);
        return ResponseEntity.ok().build();
    }

    // 장소 숨김 // 삭제가 있는가? 숨김 아닌가?
    @PatchMapping("/{place-uuid}/hide")
    @Operation(summary = "장소 숨김")
    public ResponseEntity hidePlace(@PathVariable("place-uuid") String placeUuid){
        placeService.hide(placeUuid);
        return ResponseEntity.ok().build();
    }

    // 이 아래는 찜하기라 좀 다름
    // 장소 찜하기
    @PatchMapping("/{place-uuid}/hearts/{user-uuid}")
    @Operation(summary = "장소 찜하기")
    public ResponseEntity heartplace(@PathVariable("place-uuid") String placeUuid, @PathVariable("user-uuid") String userUuid){
        boolean heart = placeService.updateHeart(placeUuid, userUuid);
        return ResponseEntity.ok(heart);
    }

    @GetMapping("/my-places/{user-uuid}")
    @Operation(summary = "찜한 장소 보기")
    public ResponseEntity getMyPlaces(@PathVariable("user-uuid") String userUuid){
        List<Place> placeList = placeService.getMyPlaces(userUuid);
        List<PlaceDto.SimpleResponse> simpleResponses = placeMapper.placesToPlaceDtoSimpleResponses(placeList);
        return ResponseEntity.ok(simpleResponses);
    }

    // 장소 수정 신고 /{place-uuid} // 어떻게 할건지 미정, 신고 도메인에서 처리 예정
    @PostMapping("/{place-uuid}")
    @Operation(summary = "장소 신고하기")
    public ResponseEntity reportPlace(@PathVariable("place-uuid") String placeUuid, @Valid @RequestBody PlaceDto.ReportPost requestBody){
        return ResponseEntity.ok(placeService.reportPlace(placeUuid, requestBody.getUserUuid(), requestBody.getContent()));
    }
}

