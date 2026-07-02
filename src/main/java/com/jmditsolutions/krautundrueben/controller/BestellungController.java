package com.jmditsolutions.krautundrueben.controller;

import com.jmditsolutions.krautundrueben.dto.DurchschnittsNaehrwerteDto;
import com.jmditsolutions.krautundrueben.dto.KundeBestellAnzahlDto;
import com.jmditsolutions.krautundrueben.entity.Bestellung;
import com.jmditsolutions.krautundrueben.service.BestellungService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/bestellung")
public class BestellungController {

    private final BestellungService bestellungService;

    public BestellungController(BestellungService bestellungService) {
        this.bestellungService = bestellungService;
    }

    @GetMapping("/durchschnitt-naehrwert")
    public ResponseEntity<DurchschnittsNaehrwerteDto> getDurchschnittlicheNaehrwerteByKunde(@RequestParam Integer kundennr) {
        DurchschnittsNaehrwerteDto naehrwerteDto = bestellungService.getDurchschnittsNaehrwerteByKunde(kundennr);
        return naehrwerteDto == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(naehrwerteDto);
    }

    @GetMapping("/teure")
    public ResponseEntity<List<Bestellung>> getTeureBestellungenByKundeNachname(@RequestParam String nachname, @RequestParam BigDecimal betrag) {
        List<Bestellung> bestellungen = bestellungService.getTeureBestellungenByKundeNachname(nachname, betrag);
        return bestellungen.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(bestellungen);
    }

    @GetMapping("/anzahl")
    public ResponseEntity<List<KundeBestellAnzahlDto>> getBestellAnzahlJeKunde() {
        List<KundeBestellAnzahlDto> anzahl = bestellungService.getBestellAnzahlJeKunde();
        return anzahl.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(anzahl);
    }


}
