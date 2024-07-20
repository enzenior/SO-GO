package com.enzinior.sogo.notification.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@AllArgsConstructor
public class NotificationDto {

    @Getter
    public static class Post {
        @NotBlank
        private String userUuid; // private User user;
        @NotBlank
        private String content;
    }

    @Getter
    @Setter
    public static class ResponseDefault {
        private String userUuid;
        private String content;
        private boolean isRead;
        private String notificationUuid;
    }

    @Getter
    @Setter
    public static class ResponseContent {
        private String userUuid;
        private String content;
        private boolean isRead;
        private String notificationUuid;

        private String reviewImg;
        private String reviewUuid;
    }
}