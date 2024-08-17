package com.enzinior.sogo.review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class ReviewDto {
    @Getter
    @AllArgsConstructor
    public static class Post {
        @NotBlank
        private String content;
        @NotBlank
        private String img;
        @Positive
        private int score;
        @NotBlank
        private String userUuid;
        @NotBlank
        private String placeUuid;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class Patch {
        @NotBlank
        private String content;
        @NotBlank
        private String reviewUuid;
    }

    public static class Report {
        private String userUuid;
        private String reviewUuid;
        private String content;
    }

    @AllArgsConstructor
    public static class Response {
        private String userImg;
        private String userNickname;
        private String userUuid;
        private int scrap;
        private int score;
        private int report;
        private boolean secret;
        private String reviewUuid;
        private String content;
        private String img;
        private String placeUuid;
        private String placeImg;
        private LocalDateTime createdAt;
    }


}
