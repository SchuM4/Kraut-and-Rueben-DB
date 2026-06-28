package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rechnungsadresse")
public class Rechnungsadresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rechnungsadressenr")
    private Integer id;

    @Column(name = "strasse", nullable = false, length = 100)
    private String strasse;

    @Column(name = "hausnummer", nullable = false, length = 10)
    private String hausnummer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plz", nullable = false)
    private Ort ort;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kundennr", nullable = false, unique = true)
    private Kunde kunde;
}
