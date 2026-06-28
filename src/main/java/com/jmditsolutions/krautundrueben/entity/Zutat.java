package com.jmditsolutions.krautundrueben.entity;

import com.jmditsolutions.krautundrueben.enums.Allergen;
import com.jmditsolutions.krautundrueben.enums.Ernaehrungskategorie;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "zutat")
public class Zutat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zutatennr")
    private Integer id;

    @Column(name = "bezeichnung", nullable = false, length = 255)
    private String bezeichnung;

    @Column(name = "bestand", nullable = false, precision = 10, scale = 2)
    private BigDecimal bestand = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "ernaehrungskategorie")
    private Ernaehrungskategorie ernaehrungskategorie;

    @Enumerated(EnumType.STRING)
    @Column(name = "allergen")
    private Allergen allergen; // see design note above about single allergen

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "naehrstoffnr", unique = true)
    private Naehrstoffangaben naehrstoffangaben;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lieferantennr", nullable = false)
    private Lieferant lieferant;
}
