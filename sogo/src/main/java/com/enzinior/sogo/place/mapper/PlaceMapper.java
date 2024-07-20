package com.enzinior.sogo.place.mapper;


import com.enzinior.sogo.place.dto.PlaceDto;
import com.enzinior.sogo.place.entity.Place;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlaceMapper {

    Place placePostToPlace(PlaceDto.Post RequestBody);
    PlaceDto.SimpleResponse placeToSimplePlaceDto(Place place);
    PlaceDto.Response placeToResponsePlaceDto(Place place);

    List<PlaceDto.Response> placesToPlaceDto(List<Place> places);
}
