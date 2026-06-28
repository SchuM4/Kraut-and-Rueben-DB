package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "naehrstoffangaben")
public class Naehrstoffangaben {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "naehrstoffnr")
    private Integer id;

    @Column(name = "protein_g", precision = 8, scale = 2)
    private BigDecimal proteinG;

    @Column(name = "kohlenhydrate_g", precision = 8, scale = 2)
    private BigDecimal kohlenhydrateG;

    @Column(name = "zucker_g", precision = 8, scale = 2)
    private BigDecimal zuckerG;

    @Column(name = "fett_g", precision = 8, scale = 2)
    private BigDecimal fettG;

    @Column(name = "gesaett_fettsaeuren_g", precision = 8, scale = 2)
    private BigDecimal gesaettigteFettsaeurenG;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bezugseinheitnr", nullable = false)
    private Bezugseinheit bezugseinheit;
}
