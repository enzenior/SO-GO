package com.enzinior.sogo.place.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

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
    public static class reportPost {
        @NotBlank
        private String userUuid;
        @NotBlank
        private String content;
    }

    @Getter
    public static class detailDto {
        private String userUuid;
    }

    @Getter
    @Setter
    public static class SimpleResponse{
        private String placeUuid;
        private String placeName;
        private float score;
        private String tag;
        private String summary;
        private String placeImg; // 그냥 리스트 반환? 혹은 따로 저장?
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
    }


}
