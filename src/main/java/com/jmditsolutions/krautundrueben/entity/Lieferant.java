package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "lieferant", schema = "krautundrueben")
public class Lieferant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lieferantennr", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "lieferantenname", nullable = false)
    private String lieferantenname;

    @Size(max = 255)
    @Column(name = "email")
    private String email;

    @Size(max = 30)
    @Column(name = "telefon", length = 30)
    private String telefon;

    @Size(max = 100)
    @NotNull
    @Column(name = "strasse", nullable = false, length = 100)
    private String strasse;

    @Size(max = 10)
    @NotNull
    @Column(name = "hausnummer", nullable = false, length = 10)
    private String hausnummer;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plz", nullable = false)
    private Ort plz;

}