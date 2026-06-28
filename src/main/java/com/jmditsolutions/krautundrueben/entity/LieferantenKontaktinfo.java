package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "lieferantenkontaktinfo")
public class LieferantenKontaktinfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lieferantenkontaktinfonr")
    private Integer id;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "telefon", length = 30)
    private String telefon;

    @Column(name = "strasse", nullable = false, length = 100)
    private String strasse;

    @Column(name = "hausnummer", nullable = false, length = 10)
    private String hausnummer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plz", nullable = false)
    private Ort ort;

    @OneToOne(mappedBy = "kontaktinfo")
    private Lieferant lieferant;
}
