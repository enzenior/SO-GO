package com.enzinior.sogo.comment.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

import com.enzinior.sogo.audit.Auditable;
import com.enzinior.sogo.review.entity.Review;
import com.enzinior.sogo.user.entity.User;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Auditable {

    @Id
    @Column(name="comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Setter
    @Column(name = "comment_uuid", columnDefinition = "varchar(80)", unique = true)
    private String commentUuid = UUID.randomUUID().toString();

    @Lob
    @Setter
    private String content;

    @ColumnDefault("0")
    @Setter
    private int report;

    @ColumnDefault("false")
    @Setter
    private boolean secret;

    @Setter
    private String parent; // 상위 댓글이 있다면 표시.

    @Setter
    @JoinColumn(name = "user_userId")
    @ManyToOne(fetch = FetchType.LAZY) // 유저가 여러개의 댓글을 작성할 수 있음
    private User user;

    @Setter
    @JoinColumn(name = "review_reviewId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;
}
