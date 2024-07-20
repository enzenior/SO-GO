package com.enzinior.sogo.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@AllArgsConstructor
public class CommentDto {

    @Getter
    public static class Post {
        @NotBlank
        private String content;
        @NotBlank
        private String userUuid;
        @NotBlank
        private String parentUuid;
        @NotBlank
        private String reviewUuid;
    }

    @Getter
    @Setter
    public static class Response {
        private String commentUuid;
        private String userNickname;
        private String userImg;
        private String content;
        private String parentUuid;

    }


}