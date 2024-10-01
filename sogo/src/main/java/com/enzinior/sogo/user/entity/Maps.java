package com.enzinior.sogo.user.entity;

import com.enzinior.sogo.place.entity.Place;
import com.enzinior.sogo.review.entity.Review;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="maps")
@Table(name="maps")
public class Maps {
    @Id
    @Column(name = "maps_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mapsId;

    @Setter
    private int count;
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;
}
