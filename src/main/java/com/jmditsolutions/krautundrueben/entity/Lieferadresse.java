package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "lieferadresse")
public class Lieferadresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lieferadressenr")
    private Integer id;

    @Column(name = "strasse", nullable = false, length = 100)
    private String strasse;

    @Column(name = "hausnummer", nullable = false, length = 10)
    private String hausnummer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plz", nullable = false)
    private Ort ort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kundennr", nullable = false)
    private Kunde kunde;
}