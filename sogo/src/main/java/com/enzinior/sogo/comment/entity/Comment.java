package com.enzinior.sogo.comment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name="comments")
public class Comment {

    @Id
    @Column(name="comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Column(name = "comment_uuid", columnDefinition = "varchar(80)", unique = true)
    private String uuid = UUID.randomUUID().toString();

    @Lob
    private String content;

    @ColumnDefault("0")
    private int report;

    @ColumnDefault("false")
    private boolean secret;

    private String parent;

    @JoinColumn(name = "user_userId")
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "review_reviewId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;
}
