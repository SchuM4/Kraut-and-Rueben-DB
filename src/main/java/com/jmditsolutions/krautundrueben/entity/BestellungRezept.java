package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "bestellung_rezept")
public class BestellungRezept {

    @EmbeddedId
    private BestellungRezeptId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bestellungsnr")
    @OnDelete(action = OnDeleteAction.CASCADE)
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
