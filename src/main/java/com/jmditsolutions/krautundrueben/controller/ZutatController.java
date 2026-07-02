package com.jmditsolutions.krautundrueben.controller;

import com.jmditsolutions.krautundrueben.entity.Zutat;
import com.jmditsolutions.krautundrueben.service.ZutatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/zutat")
public class ZutatController {

    private final ZutatService zutatService;

    public ZutatController(ZutatService zutatService) {
        this.zutatService = zutatService;
    }

    @GetMapping("/ohne-rezept")
    public ResponseEntity<List<Zutat>> getZutatenOhneRezepte() {
        List<Zutat> zutaten = zutatService.getZutatenOhneRezept();
        return zutaten.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(zutaten);
    }

    @GetMapping("/niedrig-bestand")
    public ResponseEntity<List<Zutat>> getZutatenMitNiedrigemBestand(@RequestParam BigDecimal bestand) {
        List<Zutat> zutaten = zutatService.getZutatenMitNiedrigemBestand(bestand);
        return zutaten.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(zutaten);
    }

    @GetMapping("/lieferant")
    public ResponseEntity<List<Zutat>> getZutatenByLieferant(@RequestParam String lieferant) {
        List<Zutat> zutaten = zutatService.getZutatenByLieferant(lieferant);
        return zutaten.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(zutaten);
    }


}
