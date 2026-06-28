package com.jmditsolutions.krautundrueben.entity;

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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "lieferantenkontaktinfonr", nullable = false, unique = true)
    private LieferantenKontaktinfo kontaktinfo;
}
