package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "bestellung", schema = "krautundrueben")
public class Bestellung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bestellungsnr", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kundennr", nullable = false)
    private Kunde kundennr;

    @NotNull
    @ColumnDefault("CURRENT_DATE")
    @Column(name = "bestelldatum", nullable = false)
    private LocalDate bestelldatum;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "rechnungsbetrag", nullable = false, precision = 10, scale = 2)
    private BigDecimal rechnungsbetrag;

}