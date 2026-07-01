package com.jmditsolutions.krautundrueben.repository;

import com.jmditsolutions.krautundrueben.entity.Zutat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ZutatRepository extends JpaRepository<Zutat, Integer> {

    // Auswahl aller Zutaten, die bisher keinem Rezept zugeordnet sind (Subquery)
    @Query("""
        SELECT z FROM Zutat z
        WHERE z NOT IN (
            SELECT rz.zutatnr FROM RezeptZutat rz
        )
        """)
    List<Zutat> findZutatenOhneRezept();

    // Extra-Abfrage: Zutaten mit niedrigem Bestand, inkl. Lieferant
    @Query("""
        SELECT z FROM Zutat z
        JOIN FETCH z.lieferantennr l
        WHERE z.bestand < :minBestand
        ORDER BY z.bestand ASC
        """)
    List<Zutat> findZutatenMitNiedrigemBestand(@Param("minBestand") BigDecimal minBestand);

    // Extra-Abfrage: Zutaten eines bestimmten Lieferanten
    List<Zutat> findByLieferantennr_Lieferantenname(String lieferantenname);
}
