package com.enzinior.sogo.notification.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enzinior.sogo.notification.entity.Notification;
import com.enzinior.sogo.notification.repository.NotificationRepository;
import com.enzinior.sogo.review.entity.Review;
import com.enzinior.sogo.user.entity.User;
import com.enzinior.sogo.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;


    // 알림 전체 조회
    @Override
    @Transactional(readOnly = true)
    public List<Notification> searchNotification(String userUuid){
        User user = userService.findUser(userUuid);
        return notificationRepository.findAllByUserUuid(userUuid);
    }

    // 읽음처리
    @Override
    public void readAllNotification(String userUuid) {

        User user = userService.findUser(userUuid);

        List<Notification> notificationList = notificationRepository.findAllByIsRead(userUuid);
        for(Notification n : notificationList){
            n.updateIsRead(true);
        }
    }

    // 안읽은 알림 갯수 반환
    @Override
    public long readYetCntNotification(String userUuid) {
        return notificationRepository.countByYetRead(userUuid);
    }

    // 알림 생성
    @Override // 연계될 리뷰가 있을 때
    public Notification createNotificationByReview(User user, String content, Review review){ // review를 줄지 review uuid를 받을지
        Notification notification = new Notification();
        notification.createNotification(user, content, review);
        return notificationRepository.save(notification);
    }

    @Override // 신고, 새로운 유저 가입시
    public Notification createNotification(User user, String content){
        Notification notification = new Notification();
        notification.createNotification(user, content);
        return notificationRepository.save(notification);
    }

}
