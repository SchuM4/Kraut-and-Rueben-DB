package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "bestellung_rezept", schema = "krautundrueben")
public class BestellungRezept {
    @EmbeddedId
    private BestellungRezeptId id;

    @MapsId("bestellungsnr")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "bestellungsnr", nullable = false)
    private Bestellung bestellungsnr;

    @MapsId("rezeptnr")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rezeptnr", nullable = false)
    private Rezept rezeptnr;

    @NotNull
    @Column(name = "menge", nullable = false)
    private Integer menge;

}