package com.enzinior.sogo.notification.service;

import com.enzinior.sogo.notification.entity.Notification;

import java.util.List;

public interface NotificationService {

    List<Notification> readAllNotification(String userUuid);

    Notification createNotification(User user, String content);

    void hideNotification(String userUuid);

    long readCntNotification(String userUuid);
}
