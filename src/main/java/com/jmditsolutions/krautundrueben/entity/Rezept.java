package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "rezept", schema = "krautundrueben")
public class Rezept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rezeptnr", nullable = false)
    private Integer id;

    @Size(max = 150)
    @NotNull
    @Column(name = "bezeichnung", nullable = false, length = 150)
    private String bezeichnung;

    @Column(name = "beschreibung", length = Integer.MAX_VALUE)
    private String beschreibung;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "portionen", nullable = false)
    private Integer portionen;

    @Size(max = 255)
    @Column(name = "bild")
    private String bild;

}