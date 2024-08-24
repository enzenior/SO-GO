package com.enzinior.sogo.notification.mapper;

import java.util.List;

import com.enzinior.sogo.comment.dto.CommentDto;
import com.enzinior.sogo.comment.entity.Comment;
import com.enzinior.sogo.notification.dto.NotificationDto;
import com.enzinior.sogo.notification.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(source = "user.userUuid", target = "userUuid")
    @Mapping(source = "review.img", target = "reviewImg")
    @Mapping(source = "review.reviewUuid", target = "reviewUuid")
    NotificationDto.Response notificationToNotificationResponse(Notification notification);

    List<NotificationDto.Response> notificationsToNotificationResponses(List<Notification> notifications);

}
