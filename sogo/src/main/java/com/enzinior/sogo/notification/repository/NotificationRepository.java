package com.enzinior.sogo.notification.repository;

import com.enzinior.sogo.notification.entity.Notification;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n JOIN n.user u WHERE u.userUuid = :userUuid")
    @EntityGraph(attributePaths = {"user", "review"})
    List<Notification> findAllByUserUuid(String userUuid);

    @Query("SELECT n FROM Notification n JOIN n.user u WHERE u.userUuid = :userUuid AND n.isRead = false")
    @EntityGraph(attributePaths = {"user", "review"})
    List<Notification> findAllByIsRead(String userUuid);

    @Query("SELECT COUNT(n) FROM Notification n JOIN n.user u WHERE u.userUuid = :userUuid AND n.isRead = false")
    long countByYetRead(String userUuid);

    void deleteAllByReviewReviewUuid(String reviewReviewUuid);
}
