package com.enzinior.sogo.review.entity;

import com.enzinior.sogo.audit.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Review extends Auditable {
    @Setter
    @Id
    @Column(name = "REVIEW_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;

    @Setter
    @ColumnDefault("0")
    private int report;
    @Setter
    @ColumnDefault("0")
    private int scrap;
    @Setter
    @ColumnDefault("0")
    private int maxCnt;
    @Setter
    @ColumnDefault("0")
    private int score;
    private boolean secret;

    @Column(columnDefinition = "varchar(80)", unique = true)
    private String reviewUuid = UUID.randomUUID().toString();

    @Setter
    @Lob
    private String content;

    @Setter
    @Column(columnDefinition = "varchar(1024)")
    private String img;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "USER_ID")
//    private User user;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "PLACE_ID")
//    private Place place;
}
