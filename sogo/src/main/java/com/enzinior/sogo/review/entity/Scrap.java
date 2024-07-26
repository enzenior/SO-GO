package com.enzinior.sogo.review.entity;

import com.enzinior.sogo.user.entity.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Scrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scrapId;

    @JoinColumn(name = "USER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "REVIEW_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;
}
