package com.enzinior.sogo.place.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class PlaceDto {

    @Getter
    public static class Post {
        @NotBlank
        private String placeName;
        @NotBlank
        private String address;
        @Positive
        private double lat;
        @Positive
        private double lng;
    }

    @Getter
    public static class ReportPost {
        @NotBlank
        private String userUuid;
        @NotBlank
        private String content;
    }

    @Getter
    public static class DetailDto {
        private String userUuid;
    }

    @Getter
    @Setter
    public static class ReviewUuidDto{
        private String placeUuid;
    }

    @Getter
    @Setter
    public static class SimpleResponse{
        private String placeUuid;
        private String placeName;
        private float score;
        private String tag;
        private String summary;
        private Integer type;
        private String placeImgs;
        private double lat;
        private double lng;
    }

    @Getter
    @Setter
    public static class Response{
        private String placeUuid;
        private String placeName;
        private String address;
        private double lat;
        private double lng;
        private float score;
        private long heartCnt;
        private boolean userHeart;
        private boolean hide;
        private String tag;
        private String summary;
        private String placeImgs;
        private String number;
        private String time;
        private String date;
        private String website;
        private Integer type;

        private Boolean parking;
        private Boolean wheelchair;
        private Boolean elevator;
        private Boolean pet;

    }


}
