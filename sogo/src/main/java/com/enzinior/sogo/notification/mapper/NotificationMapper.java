package com.enzinior.sogo.notification.mapper;

import com.enzinior.sogo.comment.dto.CommentDto;
import com.enzinior.sogo.comment.entity.Comment;
import com.enzinior.sogo.notification.dto.NotificationDto;
import com.enzinior.sogo.notification.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public class NotificationMapper {

    @Mapping(source = "userUuid", target = "user.uuid")
    Notification notificationPostToNotification(NotificationDto.Post requestBody);

    @Mapping(source = "userUuid", target = "user.uuid")
    NotificationDto.ResponseDefault notificationDefaultToNotificationDto(Notification notification);

    @Mapping(source = "userUuid", target = "user.uuid")
    @Mapping(source = "reviewImg", target = "review.img")
    @Mapping(source = "reviewUuid", target = "review.uuid")
    NotificationDto.ResponseContent notificationReviewToNotificationDto(Notification notification);


}
