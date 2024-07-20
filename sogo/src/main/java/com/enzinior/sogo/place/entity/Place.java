package com.enzinior.sogo.place.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.text.DecimalFormat;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name="places")
public class Place {
    @Setter
    @Id
    @Column(name="place_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Column(columnDefinition = "varchar(60)")
    private String placeName;

    @Setter
    @Lob
    private String placeDescription;

    @Setter
    @NotNull
    private String placeTag;

    @Setter
    @NotNull
    private String placeSummary

    @ColumnDefault("0")
    @Setter
    private int heart;

    @ColumnDefault("0")
    @Setter
    private float score;

    @NotNull
    private double lat;

    @NotNull
    private double lng;

    @NotNull
    @Column(name = "place_uuid", columnDefinition = "varchar(80)", unique = true)
    private String uuid = UUID.randomUUID().toString();

}
