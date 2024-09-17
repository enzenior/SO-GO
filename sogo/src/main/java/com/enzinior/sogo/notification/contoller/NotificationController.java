package com.enzinior.sogo.notification.contoller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enzinior.sogo.notification.dto.NotificationDto;
import com.enzinior.sogo.notification.entity.Notification;
import com.enzinior.sogo.notification.mapper.NotificationMapper;
import com.enzinior.sogo.notification.service.NotificationService;
import com.enzinior.sogo.review.repository.ReviewRepository;
import com.enzinior.sogo.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/notifications/{user-uuid}")
public class NotificationController {
    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;
    private final UserService userService;
    private final ReviewRepository reviewRepository;

    // 알림 전체 조회 /api/notifications/{user-uuid}
    @GetMapping
    @Operation(summary = "알림 전체 조회")
    public ResponseEntity list(@PathVariable("user-uuid") String userUuid) {

        List<Notification> notificationList = notificationService.searchNotification(userUuid);
        List<NotificationDto.Response> notificationDtos = notificationMapper.notificationsToNotificationResponses(notificationList);
        notificationService.readAllNotification(userUuid);
        return ResponseEntity.ok(notificationDtos);

    }

    // 안읽은 알림 갯수 반환 /api/notification/{user-uuid}/yet
    @GetMapping("/yet")
    @Operation(summary = "안읽은 알림 수 조회")
    public ResponseEntity count(@PathVariable("user-uuid") String userUuid){
        long cnt = notificationService.readYetCntNotification(userUuid);
        return ResponseEntity.ok(cnt);
    }

}
