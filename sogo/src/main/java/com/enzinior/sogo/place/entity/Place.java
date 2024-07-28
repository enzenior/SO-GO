package com.enzinior.sogo.place.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Place {

    @Id
    @Column(name="place_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long placeId;

    @Setter
    @Column(columnDefinition = "varchar(60)")
    private String placeName;

    @Setter
    @Lob
    private String placeDescription;

    @Setter
    private String placeTag;

    @Setter
    private String placeSummary;

    @ColumnDefault("0")
    @Setter
    private int heart;

    @ColumnDefault("0")
    @Setter
    private float score;

    @Setter
    private double lat;

    @Setter
    private double lng;

    @Setter
    @ColumnDefault("false")
    private boolean hide;

    @Column(name = "place_uuid", columnDefinition = "varchar(80)", unique = true)
    private String uuid = UUID.randomUUID().toString();

    @OneToMany(mappedBy = "heart")
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "placeImg")
    private List<PlaceImg> placeImgs = new ArrayList<>();

}
