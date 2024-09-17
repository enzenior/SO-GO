package com.enzinior.sogo.auth.repository;

import com.enzinior.sogo.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {


    Boolean existsByRefreshToken(String refresh);

    void deleteByRefreshToken(String refresh);

    Boolean existsRefreshTokenByRefreshToken(String refresh);

    Optional<RefreshToken> findByUserUserUuid(String userUuid);
}
