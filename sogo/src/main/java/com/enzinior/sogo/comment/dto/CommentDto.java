package com.enzinior.sogo.comment.dto;

import jakarta.validation.constraints.Positive;
import lombok.*;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    @Getter
    public static class Post {
        private String content;
        private String userUuid;
        private String parentUuid;
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