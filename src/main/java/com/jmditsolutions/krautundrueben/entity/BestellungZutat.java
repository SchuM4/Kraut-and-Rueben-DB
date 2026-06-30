package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "bestellung_zutat", schema = "krautundrueben")
public class BestellungZutat {
    @EmbeddedId
    private BestellungZutatId id;

    @MapsId("bestellungsnr")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "bestellungsnr", nullable = false)
    private Bestellung bestellungsnr;

    @MapsId("zutatnr")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "zutatnr", nullable = false)
    private Zutat zutatnr;

    @NotNull
    @Column(name = "menge", nullable = false, precision = 10, scale = 2)
    private BigDecimal menge;

}