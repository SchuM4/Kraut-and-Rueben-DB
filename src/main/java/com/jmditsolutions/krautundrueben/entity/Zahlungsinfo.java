package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
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
