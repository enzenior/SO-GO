package com.enzinior.sogo.user.entity;

import com.enzinior.sogo.user.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="users")
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String socialId;

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

    @ColumnDefault("false")
    private boolean state;

    private String refreshToken;

    public User(UserDto.Patch patch) {
        this.nickname = patch.getNickname();
        this.img = patch.getImg();
        this.userUuid = patch.getUserUuid();
        this.sentence = patch.getSentence();
    }

    public User(UserDto.SignUp signUp) {
        this.nickname = signUp.getNickname();
        this.socialId = signUp.getId();
        this.email = signUp.getEmail();
        this.site = signUp.isSite();
        this.role = signUp.getRole();
        this.report = signUp.getReport();
        this.userUuid = signUp.getUserUuid();
        this.sentence = signUp.getSentence();
    }

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

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeImg(String img) {
        this.img = img;
    }

    public void changeSentence(String sentence) {
        this.sentence = sentence;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeId(String id) {
        this.socialId = id;
    }

    public void changeState(boolean state) {
        this.state = state;
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

}
