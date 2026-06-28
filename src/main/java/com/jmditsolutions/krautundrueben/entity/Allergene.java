package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "allergene")
public class Allergene {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "allergenenr")
    private Integer id;

    @Column(name = "bezeichnung", nullable = false, length = 100)
    private String bezeichnung;
}
