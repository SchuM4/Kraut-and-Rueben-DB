package com.jmditsolutions.krautundrueben.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BestellungDto(
        Integer id,
        LocalDate bestelldatum,
        BigDecimal rechnungsbetrag
) {}
