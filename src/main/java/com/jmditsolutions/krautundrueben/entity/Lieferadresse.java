package com.jmditsolutions.krautundrueben.entity;

import com.jmditsolutions.krautundrueben.embeddable.Adresse;
import jakarta.persistence.*;

@Entity
@Table(name = "lieferadresse")
public class Lieferadresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lieferadressenr")
    private Integer id;

    @Embedded
    private Adresse adresse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kundennr", nullable = false)
    private Kunde kunde;
}