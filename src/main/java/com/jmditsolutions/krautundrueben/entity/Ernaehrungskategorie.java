package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ernaehrungskategorie")
public class Ernaehrungskategorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ernaehrungskategorienr")
    private Integer id;

    @Column(name = "bezeichnung", nullable = false, length = 100)
    private String bezeichnung;
}
