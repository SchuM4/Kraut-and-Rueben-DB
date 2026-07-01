package com.jmditsolutions.krautundrueben.dto;


public record DurchschnittsNaehrwerteDto(
        Double avgKalorien,
        Double avgProtein,
        Double avgKohlenhydrate,
        Double avgFett,
        Double avgZucker
) {
}
