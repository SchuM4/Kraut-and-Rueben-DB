package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "rezept_zutat")
public class RezeptZutat {

    @EmbeddedId
    private RezeptZutatId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("rezeptnr")
    @JoinColumn(name = "rezeptnr")
    private Rezept rezept;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("zutatnr")
    @JoinColumn(name = "zutatnr")
    private Zutat zutat;

    @Column(name = "menge", nullable = false, precision = 10, scale = 2)
    private BigDecimal menge;

    @Column(name = "einheit", length = 20)
    private String einheit;

    @Embeddable
    public static class RezeptZutatId implements Serializable {
        @Column(name = "rezeptnr")
        private Integer rezeptnr;
        @Column(name = "zutatnr")
        private Integer zutatnr;
        // equals + hashCode required
    }
}
