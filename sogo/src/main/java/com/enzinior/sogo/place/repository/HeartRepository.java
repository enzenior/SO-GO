package com.enzinior.sogo.place.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.enzinior.sogo.place.entity.Heart;
import com.enzinior.sogo.place.entity.Place;
import com.enzinior.sogo.user.entity.User;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    @Query("SELECT h FROM Heart h JOIN h.place p JOIN h.user u WHERE p.placeUuid = :placeUuid AND u.userUuid = :userUuid")
    Optional<Heart> findHeartByPlaceAndUser(String placeUuid, String userUuid);

    @Query("SELECT h.place FROM Heart h WHERE h.user.userUuid = :userUuid")
    List<Place> findPlacesByUser(String userUuid);
    // 장소 등록
    // 장소 수정
    // 장소 삭제

    // 장소 검증


}
