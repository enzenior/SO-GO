package com.enzinior.sogo.place.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class PlaceImg {

    @Id
    @Column(name = "placeImg_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long placeImgId;

    @Setter
    @Column(name = "img", columnDefinition = "varchar(1024)")
    private String img;

    @JoinColumn(name = "place_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Place place;

}
