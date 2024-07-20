package com.enzinior.sogo.user.repository;

import com.enzinior.sogo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserUuid(String userUuid);

    Boolean existsByNickname(String nickname);

    Optional<User> findByRefreshToken(String refreshToken);

}
