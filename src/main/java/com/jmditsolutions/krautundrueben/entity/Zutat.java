package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "zutat", schema = "krautundrueben")
public class Zutat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zutatennr", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lieferantennr", nullable = false)
    private Lieferant lieferantennr;

    @Size(max = 255)
    @NotNull
    @Column(name = "bezeichnung", nullable = false)
    private String bezeichnung;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "bestand", nullable = false, precision = 10, scale = 2)
    private BigDecimal bestand;

    @Column(name = "protein_g", precision = 8, scale = 2)
    private BigDecimal proteinG;

    @Column(name = "kohlenhydrate_g", precision = 8, scale = 2)
    private BigDecimal kohlenhydrateG;

    @Column(name = "zucker_g", precision = 8, scale = 2)
    private BigDecimal zuckerG;

    @Column(name = "fett_g", precision = 8, scale = 2)
    private BigDecimal fettG;

    @Column(name = "gesaett_fettsaeuren_g", precision = 8, scale = 2)
    private BigDecimal gesaettFettsaeurenG;

    @Column(name = "ballaststoffe_g", precision = 8, scale = 2)
    private BigDecimal ballaststoffeG;

    @Column(name = "kalorien_kcal", precision = 8, scale = 2)
    private BigDecimal kalorienKcal;

    @Column(name = "kalorien_kj", precision = 8, scale = 2)
    private BigDecimal kalorienKj;

    @Column(name = "natrium_g", precision = 8, scale = 2)
    private BigDecimal natriumG;

    @Column(name = "bezugsmenge", precision = 8, scale = 2)
    private BigDecimal bezugsmenge;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bezugseinheitnr", nullable = false)
    private Bezugseinheit bezugseinheitnr;

}