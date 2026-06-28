package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "bestellung_zutat")
public class BestellungZutat {

    @EmbeddedId
    private BestellungZutatId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bestellungsnr")
    @JoinColumn(name = "bestellungsnr")
    private Bestellung bestellung;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("zutatnr")
    @JoinColumn(name = "zutatnr")
    private Zutat zutat;

    @Column(name = "menge", nullable = false, precision = 10, scale = 2)
    private BigDecimal menge;

    @Embeddable
    public static class BestellungZutatId implements Serializable {
        @Column(name = "bestellungsnr") private Integer bestellungsnr;
        @Column(name = "zutatnr")       private Integer zutatnr;
    }
}
