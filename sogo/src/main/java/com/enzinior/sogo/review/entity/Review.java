package com.enzinior.sogo.review.entity;

import com.enzinior.sogo.audit.Auditable;
import com.enzinior.sogo.place.entity.Place;
import com.enzinior.sogo.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Review extends Auditable {
    @Setter
    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Setter
    @ColumnDefault("0")
    private Integer report;
    @Setter
    @ColumnDefault("0")
    private Integer scrap;
    @Setter
    @ColumnDefault("0")
    private Integer maxCnt;
    @Setter
    @ColumnDefault("0")
    private Integer score;
    @Setter
    @ColumnDefault("false")
    private Boolean secret;

    @Setter
    @Column(columnDefinition = "varchar(80)", unique = true)
    private String reviewUuid = UUID.randomUUID().toString();

    @Setter
    @Lob
    private String content;

    @Setter
    @Column(columnDefinition = "varchar(1024)")
    private String img;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "review")
    private List<Scrap> scraps = new ArrayList<>();

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    public Review(String reviewUuid) {
        this.reviewUuid = reviewUuid;
    }
}
