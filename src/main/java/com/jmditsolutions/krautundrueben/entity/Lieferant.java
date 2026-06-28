package com.jmditsolutions.krautundrueben.entity;

import com.jmditsolutions.krautundrueben.embeddable.Adresse;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lieferant")
public class Lieferant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lieferantennr")
    private Integer id;

    @Column(name = "lieferantenname", nullable = false, length = 255)
    private String name;

    // Kontaktinfo is 1:1 and owned by Lieferant — embed it
    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "telefon", length = 30)
    private String telefon;

    @Embedded
    private Adresse adresse;

    @OneToMany(mappedBy = "lieferant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Zutat> zutaten = new ArrayList<>();
}
