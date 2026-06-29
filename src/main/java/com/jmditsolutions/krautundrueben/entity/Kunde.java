package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "kunde")
public class Kunde {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kundennr")
    private Integer id;

    @Column(name = "vorname", nullable = false, length = 100)
    private String vorname;

    @Column(name = "nachname", nullable = false, length = 150)
    private String nachname;

    @Column(name = "geburtsdatum")
    private LocalDate geburtsdatum;

    @Column(name = "emailadresse", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "telefonnummer", length = 30)
    private String telefon;

    @OneToOne(mappedBy = "kunde", cascade = CascadeType.ALL, orphanRemoval = true)
    private Rechnungsadresse rechnungsadresse;

    @OneToMany(mappedBy = "kunde", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lieferadresse> lieferadressen = new ArrayList<>();

    @OneToMany(mappedBy = "kunde", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bestellung> bestellungen = new ArrayList<>();
}

