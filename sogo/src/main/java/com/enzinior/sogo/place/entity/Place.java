package com.enzinior.sogo.place.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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
    private double lat;

    @Setter
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

    // @Setter
    // @Convert(converter = ImagesConverter.class)
    // private List<String> placeImgs;

    // 생각해보니 hearts에 대한 리스트 값이 필요가 없다.
    // @OneToMany(mappedBy = "place")
    // private List<Heart> hearts = new ArrayList<>();

}
