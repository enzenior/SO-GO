package com.enzinior.sogo.notification.repository;

import com.enzinior.sogo.comment.entity.Comment;
import com.enzinior.sogo.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findByUuid(String uuid);

}
