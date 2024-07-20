package com.enzinior.sogo.notification.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="notifications")
public class Notification {

    @Id
    @Column(name="notification_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "notification_uuid", columnDefinition = "varchar(80)", unique = true)
    private String uuid;

    @Lob
    private String content;

    @ColumnDefault("true")
    private boolean isRead;


    @JoinColumn(name = "user_id")
    private User user;
    @JoinColumn(name = "review_id")
    private Review review;

}
