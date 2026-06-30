package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "zutat_allergen", schema = "krautundrueben")
public class ZutatAllergen {
    @EmbeddedId
    private ZutatAllergenId id;

    @MapsId("zutatennr")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "zutatennr", nullable = false)
    private Zutat zutatennr;

    @MapsId("allergennr")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "allergennr", nullable = false)
    private Allergen allergennr;

}