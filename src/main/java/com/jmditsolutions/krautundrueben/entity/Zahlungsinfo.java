package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "zahlungsinfo")
public class Zahlungsinfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zahlungsinfonr")
    private Integer id;

    @Column(name = "zahlungsart", nullable = false, length = 100)
    private String zahlungsart;

    @Column(name = "zahlungsstatus", nullable = false, length = 100)
    private String zahlungsstatus;

    @Column(name = "zahlungsdatum")
    private LocalDate zahlungsdatum;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bestellungsnr", nullable = false)
    private Bestellung bestellung;
}
