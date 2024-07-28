package com.enzinior.sogo.place.contoller;

import com.enzinior.sogo.notification.entity.Notification;
import com.enzinior.sogo.place.dto.PlaceDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.enzinior.sogo.place.service.PlaceService;
import com.enzinior.sogo.place.mapper.PlaceMapper;
import com.enzinior.sogo.place.entity.Place;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/places")
public class PlaceController{

    private final PlaceService placeService;
    private final PlaceMapper placeMapper;

    // 장소 검색
    @GetMapping
    public ResponseEntity search(@Valid @RequestParam String word){
        List<Place> placeList = placeService.searchByCon(word);
        return ResponseEntity.ok(placeMapper.placesToPlaceDtoSimpleResponses(placeList));

    }

    // 리뷰 등록시 장소 검색 /search  || kakao map이 어떤 정보를 주는지 알아야함..
    // 요청이 왔는데 장소 이름만 다르고 위도 경도가 같을 경우에는 어떤 값을 다시 줄건지?
    @PostMapping("/search")
    public ResponseEntity whenCreateReview(@Valid @RequestBody PlaceDto.Post requestBody){
        Place place = placeMapper.placePostToPlace(requestBody);
        String result = placeService.searchWhenCreateReview(place);
        if(result==null) {
            postPlace(requestBody); // 장소 등록 controller 호출, uri 생성 때문
            result = placeService.searchWhenCreateReview(place);
        }
        return ResponseEntity.ok(result);
    }

    // 장소 등록
    @PostMapping
    public ResponseEntity postPlace(@Valid @RequestBody PlaceDto.Post requestBody){
        Place place = placeMapper.placePostToPlace(requestBody);
        Place createPlace = placeService.createPlace(place);

        URI location = UriCreator.createUri("/places", createPlace.getPlaceId();
        return ResponseEntity.created(location).build();
    }

    // 장소 상세 페이지 /{place-uuid}
    @GetMapping("/{place-uuid}")
    public ResponseEntity getPlaceDetail(@PathVariable("place-uuid") String placeUuid){
        return ResponseEntity.ok(placeMapper.placeToPlaceDtoResponse(placeService.getPlace(placeUuid)));
    }

    // 장소 수정  //
    @PatchMapping("/{place-uuid}")
    public ResponseEntity updatePlace(@Valid @RequestBody PlaceDto.Post requestBody, @PathVariable("place-uuid") String placeUuid){
        Place place = placeMapper.placePostToPlace(requestBody);
        placeService.update(place, placeUuid);
        return ResponseEntity.ok();
    }

    // 장소 숨김 /{place-uuid}  // 삭제가 있는가? 숨김 아닌가?
    @PatchMapping("/{place-uuid}/hide")
    public ResponseEntity hidePlace(@PathVariable("place-uuid") String placeUuid){
        return ResponseEntity.ok(placeService.hide(placeUuid));
    }

    // 이 아래는 찜하기라 좀 다름
    // 장소 찜하기 /hearts

    // 내가 찜한 장소 조회 /my-places/{user-uuid}

//    // 장소 수정 신고 /{place-uuid} // 어떻게 할건지 미정, 신고 도메인에서 처리 예정
//    @PostMapping

    // 장소 점수 등록() -> 서비스에만 추가! // 점수 계산해서 주면 db에 갱신


}

