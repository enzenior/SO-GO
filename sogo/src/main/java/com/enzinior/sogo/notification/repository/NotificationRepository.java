package com.enzinior.sogo.notification.repository;

import com.enzinior.sogo.comment.entity.Comment;
import com.enzinior.sogo.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n JOIN FETCH n.user u WHERE u.userUuid = :userUuid")
    List<Notification> findAllByUserUuid(String userUuid);

    @Query("SELECT n FROM Notification n JOIN FETCH n.user u WHERE u.userUuid = :userUuid AND n.isRead = false")
    List<Notification> findAllByIsRead(String userUuid);

    @Query("SELECT COUNT(n) FROM Notification n JOIN FETCH n.user u WHERE u.userUuid = :userUuid AND n.isRead = false")
    long countByYetRead(String userUuid);

}
