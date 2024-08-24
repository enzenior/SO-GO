package com.enzinior.sogo.notification.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import com.enzinior.sogo.review.entity.Review;
import com.enzinior.sogo.user.entity.User;

@Entity
@Getter
@NoArgsConstructor
public class Notification {

    @Id
    @Column(name="notification_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long notificationId;

    @Column(name = "notification_uuid", columnDefinition = "varchar(80)", unique = true)
    private String notificationUuid;

    @Lob
    @Setter
    private String content;

    @Setter
    @ColumnDefault("false")
    private boolean isRead;

    @JoinColumn(name = "user_id")
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "review_id")
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

}
