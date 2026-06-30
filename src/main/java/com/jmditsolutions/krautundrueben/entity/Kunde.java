package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "kunde", schema = "krautundrueben")
public class Kunde {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kundennr", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "vorname", nullable = false, length = 100)
    private String vorname;

    @Size(max = 150)
    @NotNull
    @Column(name = "nachname", nullable = false, length = 150)
    private String nachname;

    @Column(name = "geburtsdatum")
    private LocalDate geburtsdatum;

    @Size(max = 255)
    @NotNull
    @Column(name = "emailadresse", nullable = false)
    private String emailadresse;

    @Size(max = 30)
    @Column(name = "telefonnummer", length = 30)
    private String telefonnummer;

}