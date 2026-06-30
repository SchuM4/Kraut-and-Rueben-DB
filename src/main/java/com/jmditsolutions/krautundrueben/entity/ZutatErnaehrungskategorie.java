package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "zutat_ernaehrungskategorie", schema = "krautundrueben")
public class ZutatErnaehrungskategorie {
    @EmbeddedId
    private ZutatErnaehrungskategorieId id;

    @MapsId("zutatennr")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "zutatennr", nullable = false)
    private Zutat zutatennr;

    @MapsId("ernaehrungskategorienr")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ernaehrungskategorienr", nullable = false)
    private Ernaehrungskategorie ernaehrungskategorienr;

}