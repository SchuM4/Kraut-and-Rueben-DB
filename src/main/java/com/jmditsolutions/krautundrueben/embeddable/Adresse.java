package com.jmditsolutions.krautundrueben.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Adresse {

    @Column(name = "strasse", nullable = false, length = 100)
    private String strasse;

    @Column(name = "hausnummer", nullable = false, length = 10)
    private String hausnummer;

    @Column(name = "plz", nullable = false, length = 10)
    private String plz;

//    @Column(name = "stadtname", nullable = false) // denormalized from stadt/ort join
//    private String stadtname;
}
