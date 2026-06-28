package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bezugseinheit")
public class Bezugseinheit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bezugseinheitnr")
    private Integer id;

    @Column(name = "einheit", nullable = false, length = 20)
    private String einheit;
}
