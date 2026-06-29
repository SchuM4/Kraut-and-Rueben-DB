package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bestellung")
public class Bestellung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bestellungsnr")
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kundennr", nullable = false)
    private Kunde kunde;

    @NotNull
    @ColumnDefault("CURRENT_DATE")
    @Column(name = "bestelldatum", nullable = false)
    private LocalDate bestelldatum;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "rechnungsbetrag", nullable = false, precision = 10, scale = 2)
    private BigDecimal rechnungsbetrag = BigDecimal.ZERO;

    @OneToOne(mappedBy = "bestellung", cascade = CascadeType.ALL, orphanRemoval = true)
    private Zahlungsinfo zahlungsinfo;

    @OneToMany(mappedBy = "bestellung", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BestellungZutat> bestellteZutaten = new ArrayList<>();

    @OneToMany(mappedBy = "bestellung", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BestellungRezept> bestellteRezepte = new ArrayList<>();
}
