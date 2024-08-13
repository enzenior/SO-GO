package com.enzinior.sogo.notification.service;

import com.enzinior.sogo.notification.entity.Notification;
import com.enzinior.sogo.review.entity.Review;
import com.enzinior.sogo.user.entity.User;

import java.util.List;

public interface NotificationService {

    List<Notification> searchNotification(String userUuid);

    void readAllNotification(String userUuid);

    long readYetCntNotification(String userUuid);

    Notification createNotificationByReview(User user, String content, Review review);

    Notification createNotification(User user, String content);

}
