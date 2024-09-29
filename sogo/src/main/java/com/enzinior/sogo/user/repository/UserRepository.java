package com.enzinior.sogo.user.repository;

import com.enzinior.sogo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserUuid(String userUuid);

    User findBySocialId(String id);

    Boolean existsByNickname(String nickname);

    Boolean existsByEmail(String email);

}
