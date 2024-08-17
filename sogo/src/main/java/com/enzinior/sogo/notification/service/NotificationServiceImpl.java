package com.enzinior.sogo.notification.service;

import com.enzinior.sogo.notification.entity.Notification;
import com.enzinior.sogo.notification.repository.NotificationRepository;
import com.enzinior.sogo.review.entity.Review;
import com.enzinior.sogo.user.entity.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
//    private final UserRepository userRepository;


    // 알림 전체 조회
    @Override
    @Transactional(readOnly = true)
    public List<Notification> searchNotification(String userUuid){
//        if(userRepository.verified(userUuid))
        return notificationRepository.findAllByUserUuid(userUuid);
    }

    @Override
    public void readAllNotification(String userUuid) {
//        if(userRepository.verified(userUuid))

        List<Notification> notificationList = notificationRepository.findAllByIsRead(userUuid);
        for(Notification n : notificationList){
            n.setRead(true);
        }
    }

    // 안읽은 알림 갯수 반환
    @Override
    public long readYetCntNotification(String userUuid) {
        return notificationRepository.countByYetRead(userUuid);
    }
    // 알림 전송 (service에만)
    /*
    StringBuilder content = new StringBuilder();
    content.append("⚠ ").append(user.nickname()).append("님 경고 ").append(report.getCnt()).append("회 입니다. (사유 :").append(reportContent).append(")");
    content.append("🎉  ").append(user.nickname()).append("님의 글이 스크랩 ").append(maxcnt).append("개를 돌파했습니다!");
    content.append("💡 ").append(user.nickname()).append("님이 댓글을 달았습니다!");
    content.append("❤ ").append(user.nickname()).append("님 SOGO의 여정에 합류하신 것을 환영합니다!");
     */

    @Override // 스크랩 수, 댓글 작성 시
    public Notification createNotificationByReview(User user, String content, Review review){ // review를 줄지 review uuid를 받을지
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setContent(content);
        notification.setReview(review);
        return notificationRepository.save(notification);
    }

    @Override // 신고, 새로운 유저 가입시
    public Notification createNotification(User user, String content){
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setContent(content);
        return notificationRepository.save(notification);
    }


}
