package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "bestellung_rezept")
public class BestellungRezept {

    @EmbeddedId
    private BestellungRezeptId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bestellungsnr")
    @JoinColumn(name = "bestellungsnr")
    private Bestellung bestellung;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("rezeptnr")
    @JoinColumn(name = "rezeptnr")
    private Rezept rezept;

    @Column(name = "menge", nullable = false)
    private Integer menge;

    @Embeddable
    public static class BestellungRezeptId implements Serializable {
        @Column(name = "bestellungsnr") private Integer bestellungsnr;
        @Column(name = "rezeptnr")      private Integer rezeptnr;
    }
}
