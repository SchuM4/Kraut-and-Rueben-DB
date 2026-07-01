package com.jmditsolutions.krautundrueben.service;

import com.jmditsolutions.krautundrueben.entity.Rezept;
import com.jmditsolutions.krautundrueben.entity.Zutat;
import com.jmditsolutions.krautundrueben.repository.RezeptRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RezeptService {

    private final RezeptRepository rezeptRepository;

    public RezeptService(RezeptRepository rezeptRepository) {
        this.rezeptRepository = rezeptRepository;
    }

    public List<Zutat> getZutatenByRezeptname(String rezeptname) {
        return rezeptRepository.findZutatenByRezeptname(rezeptname);
    }

    public List<Rezept> getRezepteByErnaehrungskategorie(String kategorie) {
        return rezeptRepository.findByErnaehrungskategorie(kategorie);
    }

    public List<Rezept> getRezepteByZutat(String zutatname) {
        return rezeptRepository.findByZutat(zutatname);
    }

    public List<Rezept> getRezepteByMaxKalorien(BigDecimal maxKalorien) {
        if (maxKalorien == null || maxKalorien.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("maxKalorien darf nicht negativ sein");
        }
        return rezeptRepository.findByMaxKalorien(maxKalorien);
    }

    public List<Rezept> getRezepteMitWenigerAlsFuenfZutaten() {
        return rezeptRepository.findRezepteMitWenigerAlsFuenfZutaten();
    }

    public List<Rezept> getWenigerAlsFuenfZutatenUndKategorie(String kategorie) {
        return rezeptRepository.findWenigerAlsFuenfZutatenUndKategorie(kategorie);
    }

    public List<Rezept> getRezepteSortiertNachBestellAnzahl() {
        return rezeptRepository.findRezepteSortiertNachBestellAnzahl();
    }

    public List<Rezept> getRezepteByMindestPortionen(Integer portionen) {
        if (portionen == null || portionen < 1) {
            throw new IllegalArgumentException("Portionen muss mindestens 1 sein");
        }
        return rezeptRepository.findByPortionenGreaterThanEqual(portionen);
    }
}
