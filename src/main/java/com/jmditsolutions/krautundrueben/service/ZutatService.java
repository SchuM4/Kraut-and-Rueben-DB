package com.jmditsolutions.krautundrueben.service;

import com.jmditsolutions.krautundrueben.entity.Zutat;
import com.jmditsolutions.krautundrueben.repository.ZutatRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ZutatService {

    private final ZutatRepository zutatRepository;

    public ZutatService(ZutatRepository zutatRepository) {
        this.zutatRepository = zutatRepository;
    }

    public List<Zutat> getZutatenOhneRezept() {
        return zutatRepository.findZutatenOhneRezept();
    }

    public List<Zutat> getZutatenMitNiedrigemBestand(BigDecimal minBestand) {
        if (minBestand == null || minBestand.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("minBestand darf nicht negativ sein");
        }
        return zutatRepository.findZutatenMitNiedrigemBestand(minBestand);
    }

    public List<Zutat> getZutatenByLieferant(String lieferantenname) {
        return zutatRepository.findByLieferantennr_Lieferantenname(lieferantenname);
    }
}
