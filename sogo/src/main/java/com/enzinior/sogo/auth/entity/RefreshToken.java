package com.enzinior.sogo.auth.entity;

import com.enzinior.sogo.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshId;

    private String refreshToken;

    private String expiration;

    @OneToOne
    @JoinColumn(name = "user_uuid", referencedColumnName = "userUuid")
    private User user;

    public RefreshToken(String refreshToken, String expiration, User user) {
        this.refreshToken = refreshToken;
        this.expiration = expiration;
        this.user = user;
    }

}
