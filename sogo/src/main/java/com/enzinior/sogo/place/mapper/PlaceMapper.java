package com.enzinior.sogo.place.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.enzinior.sogo.place.dto.PlaceDto;
import com.enzinior.sogo.place.entity.Place;

@Mapper(componentModel = "spring")
public interface PlaceMapper {
    Place placePostToPlace(PlaceDto.Post RequestBody);
    PlaceDto.SimpleResponse placeToSimplePlaceDto(Place place);
    PlaceDto.Response placeToPlaceDtoResponse(Place place);

    List<PlaceDto.SimpleResponse> placesToPlaceDtoSimpleResponses(List<Place> places);
    List<PlaceDto.Response> placesToPlaceDtoResponse(List<Place> places);
    PlaceDto.ReviewUuidDto placeToReviewUuidDto(Place place);
}
