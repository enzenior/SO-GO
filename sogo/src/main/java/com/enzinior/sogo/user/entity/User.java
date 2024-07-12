package com.enzinior.sogo.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="users")
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String img;

    @Column(nullable = false)
    private boolean site;

    @Column(columnDefinition = "VARCHAR(30) default 'ROLE_USER'")
    private String role;

    @Column(columnDefinition = "INT default 0")
    private int report;

    @Column(nullable = false, unique = true)
    private String userUuid;

    private String sentence;


//    @OneToMany
//    private List<Review> reviews = new ArrayList<>();
//    @OneToMany
//    private List<Comment> comments = new ArrayList<>();
//    @OneToMany
//    private List<Notification> notifications = new ArrayList<>();
//    @OneToMany
//    private List<Heart> hearts = new ArrayList<>();
//    @OneToMany
//    private List<Scrap> scraps = new ArrayList<>();
//    @OneToMany
//    private List<UserBadge> userBadges = new ArrayList<>();
//    @OneToMany
//    private List<Report> reports = new ArrayList<>();

}
