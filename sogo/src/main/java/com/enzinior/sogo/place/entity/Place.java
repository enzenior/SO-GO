package com.enzinior.sogo.place.entity;

import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Place {

    @Id
    @Column(name="place_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long placeId;

    @Setter
    @Column(columnDefinition = "varchar(60)", nullable = false)
    private String placeName;

    @Setter
    @Column(columnDefinition = "varchar(60)", nullable = false)
    private String placeNoEmptyName;

    @Setter
    @Column(columnDefinition = "varchar(1024)", nullable = false)
    private String address;

    @Setter
    @Lob
    private String placeDescription; // 삭제해야함

    @Setter
    private String tag;

    @Setter
    private String summary;

    @ColumnDefault("0")
    @Setter
    private int heartCnt;

    @ColumnDefault("0")
    @Setter
    private float score;

    @Setter
    @Column(nullable = false)
    private double lat; // 위도

    @Setter
    @Column(nullable = false)
    private double lng; // 경도

    @Setter
    @ColumnDefault("false")
    private boolean hide;

    @Setter
    private String placeImgs; // 사진

    @Setter
    @Column(name = "place_uuid", columnDefinition = "varchar(80)", unique = true)
    private String placeUuid = UUID.randomUUID().toString();

    @Setter
    @Column(name = "type")
    @ColumnDefault("4")
    private Integer type; // 0 관광지 1 맛집 2 숙소 3 전통시장 4 기타

    @Setter
    private String number; // 전화번호

    @Setter
    private String time; // 운영시간

    @Setter
    private String date; // 운영일

    @Setter
    private String website; // 홈페이지

    @Setter
    private String contentId; // 장소 번호

    @Setter
    private String contentTypeId; // 12(관광지), 14(문화시설), 32(숙박), 39(음식점)

    @Setter
    private Boolean parking;

    @Setter
    private Boolean wheelchair;

    @Setter
    private Boolean elevator;

    @Setter
    private Boolean pet;

}
