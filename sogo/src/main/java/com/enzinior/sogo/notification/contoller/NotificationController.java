package com.enzinior.sogo.notification.contoller;

import com.enzinior.sogo.notification.entity.Notification;
import com.enzinior.sogo.notification.service.NotificationService;
import com.enzinior.sogo.notification.mapper.NotificationMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    // 알림 전체 조회 /api/notifications/{user-uuid}
    @GetMapping("/{user-uuid}")
    public ResponseEntity list(@PathVariable("user-uuid") String userUuid) {

        List<Notification> entitylist = notificationService.searchNotification(userUuid);
        return ResponseEntity.ok(notificationMapper.notificationDefaultToNotificationDto(entitylist));

    }

    // 알림 읽음 처리 /api/notifications/{user-uuid}
    @PatchMapping("/{user-uuid}")
    public ResponseEntity hide(@PathVariable("user-uuid") String userUuid){
        return notificationService.hideNotification(userUuid);
    }


    // 안읽은 알림 갯수 반환 /api/notification/{user-uuid}/yet
    @GetMapping("/{user-uuid}/yet")
    public  ResponseEntity count(@PathVariable("user-uuid") String userUuid){
        return notificationService.readCntNotification(userUuid);
    }

}
