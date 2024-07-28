package com.enzinior.sogo.notification.service;

import com.enzinior.sogo.notification.entity.Notification;

import java.util.List;

public interface NotificationService {

    List<Notification> searchNotification(String userUuid);

    void readAllNotification(String userUuid);

    long readYetCntNotification(String userUuid);

    Notification createNotificationByReview(User user, String content, String reviewUuid);

    Notification createNotification(User user, String content);



}
