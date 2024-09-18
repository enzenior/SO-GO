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
    @Column(columnDefinition = "varchar(1024)", nullable = false)
    private String address;

    @Setter
    @Lob
    private String placeDescription;

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
    private double lat;

    @Setter
    @Column(nullable = false)
    private double lng;

    @Setter
    @ColumnDefault("false")
    private boolean hide;

    @Setter
    private String placeImgs;

    @Setter
    @Column(name = "place_uuid", columnDefinition = "varchar(80)", unique = true)
    private String placeUuid = UUID.randomUUID().toString();

    @Setter
    @Column(name = "type")
    @ColumnDefault("4")
    private int type;

    @Setter
    private String number;

    @Setter
    private String time;

    @Setter
    private String website;

    @Setter
    private String contentId;

    @Setter
    private String contentTypeId; // 12, 14, 32, 39

    @Setter
    private String placeComfort;

}
