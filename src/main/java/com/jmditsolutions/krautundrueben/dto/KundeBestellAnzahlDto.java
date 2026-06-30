package com.jmditsolutions.krautundrueben.dto;

import com.jmditsolutions.krautundrueben.entity.Kunde;

public record KundeBestellAnzahlDto(Kunde kunde, Long anzahl) {
}
