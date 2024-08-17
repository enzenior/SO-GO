package com.enzinior.sogo.maps.entity;

import com.enzinior.sogo.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
public class Maps {
    @Id
    @Setter
    @Column(name = "maps_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long mapsId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private int 서울특별시;
    private int 부산광역시;
    private int 대구광역시;
    private int 인천광역시;
    private int 광주광역시;
    private int 대전광역시;
    private int 울산광역시;
    private int 세종특별자치시;
    private int 수원시;
    private int 성남시;

    private int 의정부시;
    private int 안양시;
    private int 부천시;
    private int 광명시;
    private int 평택시;
    private int 동두천시;
    private int 안산시;
    private int 고양시;
    private int 과천시;
    private int 구리시;

    private int 남양주시;
    private int 오산시;
    private int 시흥시;
    private int 군포시;
    private int 의왕시;
    private int 하남시;
    private int 용인시;
    private int 파주시;
    private int 이천시;
    private int 안성시;

    private int 김포시;
    private int 화성시;
    private int 광주시;
    private int 양주시;
    private int 포천시;
    private int 여주시;
    private int 춘천시;
    private int 원주시;
    private int 강릉시;
    private int 동해시;

    private int 태백시;
    private int 속초시;
    private int 삼척시;
    private int 청주시;
    private int 충주시;
    private int 제천시;
    private int 천안시;
    private int 공주시;
    private int 당진시;
    private int 보령시;

    private int 아산시;
    private int 서산시;
    private int 논산시;
    private int 계룡시;
    private int 전주시;
    private int 군산시;
    private int 익산시;
    private int 정읍시;
    private int 남원시;
    private int 김제시;

    private int 목포시;
    private int 여수시;
    private int 순천시;
    private int 나주시;
    private int 광양시;
    private int 포항시;
    private int 경주시;
    private int 김천시;
    private int 안동시;
    private int 구미시;

    private int 영주시;
    private int 영천시;
    private int 상주시;
    private int 문경시;
    private int 경산시;
    private int 창원시;
    private int 진주시;
    private int 통영시;
    private int 사천시;
    private int 김해시;

    private int 밀양시;
    private int 거제시;
    private int 양산시;
    private int 제주시;
    private int 서귀포시;
}
