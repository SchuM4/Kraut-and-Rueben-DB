package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "rezept_zutat", schema = "krautundrueben")
public class RezeptZutat {
    @EmbeddedId
    private RezeptZutatId id;

    @MapsId("rezeptnr")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rezeptnr", nullable = false)
    private Rezept rezeptnr;

    @MapsId("zutatnr")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "zutatnr", nullable = false)
    private Zutat zutatnr;

    @NotNull
    @Column(name = "menge", nullable = false, precision = 10, scale = 2)
    private BigDecimal menge;

    @Size(max = 20)
    @Column(name = "einheit", length = 20)
    private String einheit;

}