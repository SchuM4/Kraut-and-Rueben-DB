package com.jmditsolutions.krautundrueben.controller;

import com.jmditsolutions.krautundrueben.entity.Lieferant;
import com.jmditsolutions.krautundrueben.entity.Rezept;
import com.jmditsolutions.krautundrueben.entity.Zutat;
import com.jmditsolutions.krautundrueben.service.RezeptService;
import com.jmditsolutions.krautundrueben.service.ZutatService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/rezept")
public class RezeptController {


    private final RezeptService rezeptService;


    public RezeptController(RezeptService rezeptService) {
        this.rezeptService = rezeptService;
    }

    @GetMapping("/zutaten")
    public ResponseEntity<List<Zutat>> getZutatenByRezeptname(@RequestParam String rezeptname) {
        List<Zutat> zutaten = rezeptService.getZutatenByRezeptname(rezeptname);
        return zutaten.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(zutaten);
    }

    @GetMapping("/ernaehrungskategorie")
    public ResponseEntity<List<Rezept>> getRezepteByErnaehrungskategorie(@RequestParam String ernaehrungskategorie) {
        List<Rezept> rezepte = rezeptService.getRezepteByErnaehrungskategorie(ernaehrungskategorie);
        return rezepte.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(rezepte);
    }

    @GetMapping("/zutat")
    public ResponseEntity<List<Rezept>> getRezepteByZutat(@RequestParam String zutatname) {
        List<Rezept> rezepte = rezeptService.getRezepteByZutat(zutatname);
        return rezepte.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(rezepte);
    }

    @GetMapping("/max-kalorien")
    public ResponseEntity<List<Rezept>> getRezepteByMaxKalorien(@RequestParam BigDecimal maxKalorien) {
        List<Rezept> rezepte = rezeptService.getRezepteByMaxKalorien(maxKalorien);
        return rezepte.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(rezepte);
    }

    @GetMapping("/wenige-zutaten")
    public ResponseEntity<List<Rezept>> getRezepteMitWenigerAlsFuenfZutaten() {
        List<Rezept> rezepte = rezeptService.getRezepteMitWenigerAlsFuenfZutaten();
        return rezepte.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(rezepte);
    }

    @GetMapping("/wenige-zutaten/kategorie")
    public ResponseEntity<List<Rezept>> getRezepteMitWenigerAlsFuenfZutatenAusBestimmterKategorie(@RequestParam String kategorie) {
        List<Rezept> rezepte = rezeptService.getWenigerAlsFuenfZutatenUndKategorie(kategorie);
        return rezepte.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(rezepte);
    }

    @GetMapping("/sortiert-nach-bestellanzahl")
    public ResponseEntity<List<Rezept>> getRezepteSortiertNachBestellanzahl() {
        List<Rezept> rezepte = rezeptService.getRezepteSortiertNachBestellAnzahl();
        return rezepte.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(rezepte);
    }

    @GetMapping("/mindest-portionen")
    public ResponseEntity<List<Rezept>> getRezepteByMindestPortionen(@RequestParam Integer mindestPortionen) {
        List<Rezept> rezepte = rezeptService.getRezepteByMindestPortionen(mindestPortionen);
        return rezepte.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(rezepte);
    }

}
