package com.enzinior.sogo.notification.service;

import com.enzinior.sogo.notification.entity.Notification;
import com.enzinior.sogo.notification.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;


    // 알림 전체 조회
    @Override
    public List<Notification> readAllNotification(String userUuid){
        return notificationRepository.findAllByUuid();
    }

    // 알림 전송 (service에만)
    /*
    StringBuilder content = new StringBuilder();
    content.append("⚠ ").append(user.nickname()).append("님 경고 ").append(report.getCnt()).append("회 입니다. (사유 :").append(reportContent).append(")");
    content.append("🎉  ").append(user.nickname()).append("님의 글이 스크랩 ").append(maxcnt).append("개를 돌파했습니다!");
    content.append("💡 ").append(user.nickname()).append("님이 댓글을 달았습니다!");
    content.append("❤ ").append(user.nickname()).append("님 SOGO의 여정에 합류하신 것을 환영합니다!");
     */
    @Override
    @Transactional
    public Notification createNotification(User user, String content, String uuid){
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setContent(content);
        return notificationRepository.save(notification);
    }

    // 알림 읽음 처리
    @Override
    @Transactional
    public void hideNotification(String userUuid){
        List<Notification> list = notificationRepository.findAllById(userUuid);
        for(Notification notification : list){
            if(notification.isRead()){
                continue;
            }
            notification.setRead()=true;
        }
    }

    // 안읽은 알림 갯수 반환
    @Override
    public long readCntNotification(String userUuid) {
        return notificationRepository.countByIsRead(userUuid);
    }



}
