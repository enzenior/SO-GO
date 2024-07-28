package com.enzinior.sogo.notification.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@AllArgsConstructor
public class NotificationDto {

    @Getter
    @Setter
    public static class Response {
        private String userUuid;
        private String content;
        private boolean isRead;
        private String notificationUuid;

        private String reviewImg;
        private String reviewUuid;
    }
}