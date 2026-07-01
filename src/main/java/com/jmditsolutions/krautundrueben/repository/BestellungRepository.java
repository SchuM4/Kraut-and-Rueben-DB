package com.jmditsolutions.krautundrueben.repository;

import com.jmditsolutions.krautundrueben.dto.DurchschnittsNaehrwerteDto;
import com.jmditsolutions.krautundrueben.dto.KundeBestellAnzahlDto;
import com.jmditsolutions.krautundrueben.entity.Bestellung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface BestellungRepository extends JpaRepository<Bestellung, Integer> {

    // Berechnung der durchschnittlichen Nährwerte aller Bestellungen eines Kunden
    @Query("""
        SELECT new com.jmditsolutions.krautundrueben.dto.DurchschnittsNaehrwerteDto(
            AVG(bz.zutatnr.kalorienKcal),
            AVG(bz.zutatnr.proteinG),
            AVG(bz.zutatnr.kohlenhydrateG),
            AVG(bz.zutatnr.fettG),
            AVG(bz.zutatnr.zuckerG)
        )
        FROM BestellungZutat bz
        WHERE bz.bestellungsnr.kundennr = :kundennr
        """)
    DurchschnittsNaehrwerteDto findDurchschnittsNaehrwerteByKunde(@Param("kundennr") Integer kundennr);

    // Bestellungen eines Kunden mit Rechnungsbetrag über X
    @Query("""
        SELECT b FROM Bestellung b
        WHERE b.kundennr.nachname = :nachname
        AND b.rechnungsbetrag > :betrag
        ORDER BY b.bestelldatum DESC
        """)
    List<Bestellung> findTeureBestellungenByKundeNachname(
            @Param("nachname") String nachname,
            @Param("betrag") BigDecimal betrag);

    // Extra-Abfrage: Anzahl Bestellungen je Kunde (Aggregat)
    @Query("""
        SELECT new com.jmditsolutions.krautundrueben.dto.KundeBestellAnzahlDto(b.kundennr, COUNT(b))
        FROM Bestellung b
        GROUP BY b.kundennr
        ORDER BY COUNT(b) DESC
        """)
    List<KundeBestellAnzahlDto> countBestellungenJeKunde();
}
