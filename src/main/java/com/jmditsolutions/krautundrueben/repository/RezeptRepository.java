package com.jmditsolutions.krautundrueben.repository;

import com.jmditsolutions.krautundrueben.entity.Rezept;
import com.jmditsolutions.krautundrueben.entity.Zutat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RezeptRepository extends JpaRepository<Rezept,Integer> {

    // Auswahl aller Zutaten eines Rezeptes nach Rezeptname
    @Query("""
        SELECT rz.zutatnr FROM RezeptZutat rz
        WHERE rz.rezeptnr.bezeichnung = :rezeptname
        """)
    List<Zutat> findZutatenByRezeptname(@Param("rezeptname") String rezeptname);

    // Auswahl aller Rezepte einer bestimmten Ernährungskategorie (alle Zutaten!)
    @Query("""
        SELECT DISTINCT rz.rezeptnr FROM RezeptZutat rz
            WHERE NOT EXISTS (
                SELECT 1 FROM RezeptZutat rz2
                WHERE rz2.rezeptnr = rz.rezeptnr
                AND NOT EXISTS (
                    SELECT 1 FROM ZutatErnaehrungskategorie zek
                    WHERE zek.zutatennr = rz2.zutatnr
                    AND zek.ernaehrungskategorienr.bezeichnung = :kategorie
        """)
    List<Rezept> findByErnaehrungskategorie(@Param("kategorie") String kategorie);

    // Auswahl aller Rezepte, die eine gewisse Zutat enthalten
    @Query("""
        SELECT DISTINCT rz.rezeptnr FROM RezeptZutat rz
        WHERE rz.zutatnr.bezeichnung = :zutatname
        """)
    List<Rezept> findByZutat(@Param("zutatname") String zutatname);

    // Rezepte, die eine bestimmte Kalorienmenge nicht überschreiten (Subselect + Aggregat)
    @Query("""
        SELECT r FROM Rezept r
        WHERE (
            SELECT COALESCE(SUM(rz.menge * rz.zutatnr.kalorienKcal / rz.zutatnr.bezugsmenge), 0)
            FROM RezeptZutat rz
            WHERE rz.rezeptnr = r AND rz.zutatnr.bezugsmenge > 0
        ) <= :maxKalorien
        """)
    List<Rezept> findByMaxKalorien(@Param("maxKalorien") BigDecimal maxKalorien);

    // Rezepte mit weniger als 5 Zutaten (Aggregat + HAVING)
    @Query("""
        SELECT rz.rezeptnr FROM RezeptZutat rz
        GROUP BY rz.rezeptnr
        HAVING COUNT(rz.zutatnr) < 5
        """)
    List<Rezept> findRezepteMitWenigerAlsFuenfZutaten();

    // Rezepte mit weniger als 5 Zutaten UND bestimmter Ernährungskategorie (alle Zutaten!)
    @Query("""
        SELECT r FROM Rezept r
        WHERE r IN (
            SELECT rz2.rezeptnr FROM RezeptZutat rz2
            GROUP BY rz2.rezeptnr
            HAVING COUNT(rz2.zutatnr) < 5
        )
        AND EXISTS (
            SELECT 1 FROM RezeptZutat rz
                    WHERE rz.rezeptnr = r
                    AND NOT EXISTS (
                        SELECT 1 FROM ZutatErnaehrungskategorie zek
                        WHERE zek.zutatennr = rz.zutatnr
                        AND zek.ernaehrungskategorienr.bezeichnung = :kategorie
                    )
        )
        """)
    List<Rezept> findWenigerAlsFuenfZutatenUndKategorie(@Param("kategorie") String kategorie);

    // Extra-Abfrage: Rezepte sortiert nach Anzahl Bestellungen (LEFT JOIN + Aggregat)
    @Query("""
        SELECT r FROM Rezept r
        LEFT JOIN BestellungRezept br ON br.rezeptnr = r
        GROUP BY r
        ORDER BY COUNT(br) DESC
        """)
    List<Rezept> findRezepteSortiertNachBestellAnzahl();

    // Extra-Abfrage: Rezepte ab einer bestimmten Portionenanzahl
    List<Rezept> findByPortionenGreaterThanEqual(Integer portionen);
}
