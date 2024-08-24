package com.enzinior.sogo.notification.contoller;

import com.enzinior.sogo.notification.dto.NotificationDto;
import com.enzinior.sogo.notification.entity.Notification;
import com.enzinior.sogo.notification.service.NotificationService;
import com.enzinior.sogo.notification.mapper.NotificationMapper;

import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/notifications/{user-uuid}")
public class NotificationController {
    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    // 알림 전체 조회 /api/notifications/{user-uuid}
    @GetMapping
    @Operation(summary = "알림 전체 조회")
    public ResponseEntity list(@PathVariable("user-uuid") String userUuid) {

        List<Notification> notificationList = notificationService.searchNotification(userUuid);
        List<NotificationDto.Response> notificationDtos = notificationMapper.notificationsToNotificationResponses(notificationList);
        notificationService.readAllNotification(userUuid);
        // 생각해보니 알림 조회를 누르자마자 모든 알림은 읽음처리 해야함
        // 그치만 조회 누르기 전 안읽은 알림들은 표시가 되어야함.
        // 로직을 위와 같이 짜면 안읽은 알림이 리스트로 먼저 저장된 후 전체 읽음 처리 되나?
        // 될듯 일단 짜
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
