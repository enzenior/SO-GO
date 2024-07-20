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
        return ResponseEntity.ok(placeMapper.placeToSimplePlaceDto(placeList));

    }

    // 리뷰 등록시 장소 검색 /search
    @PostMapping("/search")
    public ResponseEntity whenCreateReview(@Valid @RequestBody PlaceDto.Post requestBody){
        Place place = placeMapper.placePostToPlace(requestBody);
        return ResponseEntity.ok(placeService.searchWhenCreateReview(place));
    }

    // 장소 등록
    @PostMapping
    public ResponseEntity postPlace(@Valid @RequestBody PlaceDto.Post requestBody){
        Place place = placeMapper.placePostToPlace(requestBody);
        Place createPlace = placeService.createPlace(place);

        URI location = UriCreator.createUri("/places", createdPlace.getPlaceUuid());
        return ResponseEntity.created(location).build();
    }

    // 장소 상세 페이지 /{place-uuid}
    @GetMapping("/{place-uuid}")
    public ResponseEntity getPlaceDetail(@PathVariable("place-uuid") String placeUuid){
        return ResponseEntity.ok(placeMapper.placeToResponsePlaceDto(placeService.getPlace(placeUuid)));
    }

//    // 장소 수정  //
//    @PatchMapping("/{place-uuid}")
//    public ResponseEntity updatePlace(@Valid @RequestBody PlaceDto.Post requestBody){
//        Place place = placeMapper.placePostToPlace(requestBody);
//    }

    // 장소 삭제 /{place-uuid}
//    @DeleteMapping("/{place-uuid}")

//    // 장소 수정 신고 /{place-uuid} // 어떻게 할건지 미정
//    @PostMapping

    // 이 아래는 찜하기라 좀 다름
    // 장소 찜하기 /hearts

    // 내가 찜한 장소 조회 /my-places/{user-uuid}

    // 장소 점수 등록() -> 서비스에만 추가? // 점수 계산해서 주면 내가 db에 갱신


}

