package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "einkaufsauftrag")
public class Einkaufsauftrag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "einkaufsauftragnr")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lieferantennr", nullable = false)
    private Lieferant lieferant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zutatennr", nullable = false)
    private Zutat zutat;

    @Column(name = "menge", nullable = false, precision = 10, scale = 2)
    private BigDecimal menge;

    @Column(name = "nettopreis", nullable = false, precision = 10, scale = 2)
    private BigDecimal nettopreis;
}
