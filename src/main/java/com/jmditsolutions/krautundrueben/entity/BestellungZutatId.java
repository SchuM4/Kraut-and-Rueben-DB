package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class BestellungZutatId implements Serializable {
    private static final long serialVersionUID = -4464676132989999007L;
    @NotNull
    @Column(name = "bestellungsnr", nullable = false)
    private Integer bestellungsnr;

    @NotNull
    @Column(name = "zutatnr", nullable = false)
    private Integer zutatnr;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BestellungZutatId entity = (BestellungZutatId) o;
        return Objects.equals(this.bestellungsnr, entity.bestellungsnr) &&
                Objects.equals(this.zutatnr, entity.zutatnr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bestellungsnr, zutatnr);
    }

}