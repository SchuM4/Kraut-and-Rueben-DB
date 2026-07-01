package com.jmditsolutions.krautundrueben.service;

import com.jmditsolutions.krautundrueben.dto.DurchschnittsNaehrwerteDto;
import com.jmditsolutions.krautundrueben.dto.KundeBestellAnzahlDto;
import com.jmditsolutions.krautundrueben.entity.Bestellung;
import com.jmditsolutions.krautundrueben.repository.BestellungRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BestellungService {

    private final BestellungRepository bestellungRepository;

    public BestellungService(BestellungRepository bestellungRepository) {
        this.bestellungRepository = bestellungRepository;
    }

    public DurchschnittsNaehrwerteDto getDurchschnittsNaehrwerteByKunde(Integer kundennr) {
        if (kundennr == null || kundennr < 1) {
            throw new IllegalArgumentException("kundennr muss eine positive Zahl sein");
        }
        DurchschnittsNaehrwerteDto result = bestellungRepository.findDurchschnittsNaehrwerteByKunde(kundennr);
        if (result == null) {
            throw new EntityNotFoundException("Keine Bestellungen für Kunde " + kundennr + " gefunden");
        }
        return result;
    }

    public List<Bestellung> getTeureBestellungenByKundeNachname(String nachname, BigDecimal betrag) {
        if (nachname == null || nachname.isBlank()) {
            throw new IllegalArgumentException("Nachname darf nicht leer sein");
        }
        if (betrag == null || betrag.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Betrag darf nicht negativ sein");
        }
        return bestellungRepository.findTeureBestellungenByKundeNachname(nachname, betrag);
    }

    public List<KundeBestellAnzahlDto> getBestellAnzahlJeKunde() {
        return bestellungRepository.countBestellungenJeKunde();
    }
}
