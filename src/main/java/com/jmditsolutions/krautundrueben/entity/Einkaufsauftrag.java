package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "einkaufsauftrag", schema = "krautundrueben")
public class Einkaufsauftrag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "einkaufsauftragnr", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lieferantennr", nullable = false)
    private Lieferant lieferantennr;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "zutatennr", nullable = false)
    private Zutat zutatennr;

    @NotNull
    @Column(name = "menge", nullable = false, precision = 10, scale = 2)
    private BigDecimal menge;

    @NotNull
    @Column(name = "nettopreis", nullable = false, precision = 10, scale = 2)
    private BigDecimal nettopreis;

}