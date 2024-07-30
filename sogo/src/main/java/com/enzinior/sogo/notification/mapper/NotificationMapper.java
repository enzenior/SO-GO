package com.enzinior.sogo.notification.mapper;

import com.enzinior.sogo.comment.dto.CommentDto;
import com.enzinior.sogo.comment.entity.Comment;
import com.enzinior.sogo.notification.dto.NotificationDto;
import com.enzinior.sogo.notification.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(source = "userUuid", target = "user.userUuid")
    @Mapping(source = "reviewImg", target = "review.img")
    @Mapping(source = "reviewUuid", target = "review.reviewUuid")
    NotificationDto.Response notificationToNotificationResponse(Notification notification);

    List<NotificationDto.Response> notificationsToNotificationResponses(List<Notification>);

}
