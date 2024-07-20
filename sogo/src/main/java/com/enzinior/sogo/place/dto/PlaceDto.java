package com.enzinior.sogo.place.dto;

import com.enzinior.sogo.place.entity.Heart;
import com.enzinior.sogo.place.entity.PlaceImg;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.chrono.HijrahEra;

@Getter
@AllArgsConstructor
public class PlaceDto {

    @Getter
    public static class Post {
        @NotBlank
        private String placeName;
        @NotBlank
        private String placeDescription;
        @Positive
        private double lat;
        @Positive
        private double lng;
    }

    @Getter
    @Setter
    public static class SimpleResponse{
        private String placeUuid;
        private String placeName;
        private float score;
        private String tag;
        private String summary;
        private String main_img;
    }

    @Getter
    @Setter
    public static class Response{
        private String placeUuid;
        private String placeName;
        private String placeDescription;
        private double lat;
        private double lng;
//        private List<String> imgList;
    }
    // summary
    // tag



}
