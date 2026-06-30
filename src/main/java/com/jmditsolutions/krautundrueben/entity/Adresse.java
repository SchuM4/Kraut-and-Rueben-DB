package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "adresse", schema = "krautundrueben")
public class Adresse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lieferadressenr", nullable = false)
    private Integer id;

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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "kundennr", nullable = false)
    private Kunde kundennr;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "is_rechnungsadresse", nullable = false)
    private Boolean isRechnungsadresse = false;

}