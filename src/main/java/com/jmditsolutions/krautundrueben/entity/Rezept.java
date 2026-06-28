package com.jmditsolutions.krautundrueben.entity;

import com.jmditsolutions.krautundrueben.enums.Allergen;
import com.jmditsolutions.krautundrueben.enums.Ernaehrungskategorie;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rezept")
public class Rezept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rezeptnr")
    private Integer id;

    @Column(name = "bezeichnung", nullable = false, length = 150)
    private String bezeichnung;

    @Column(name = "beschreibung", columnDefinition = "TEXT")
    private String beschreibung;

    @Column(name = "portionen", nullable = false)
    private Integer portionen = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ernaehrungskategorienr")
    private Ernaehrungskategorie ernaehrungskategorie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "allergenenr")
    private Allergene allergen;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "naehrstoffnr", unique = true)
    private Naehrstoffangaben naehrstoffangaben;

    @OneToMany(mappedBy = "rezept", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RezeptZutat> zutaten = new ArrayList<>();
}
