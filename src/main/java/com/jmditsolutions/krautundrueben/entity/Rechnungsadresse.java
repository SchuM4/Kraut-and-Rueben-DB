package com.jmditsolutions.krautundrueben.entity;

import com.jmditsolutions.krautundrueben.embeddable.Adresse;
import jakarta.persistence.*;

@Entity
@Table(name = "rechnungsadresse")
public class Rechnungsadresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rechnungsadressenr")
    private Integer id;

    @Embedded
    private Adresse adresse;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kundennr", nullable = false, unique = true)
    private Kunde kunde;
}
