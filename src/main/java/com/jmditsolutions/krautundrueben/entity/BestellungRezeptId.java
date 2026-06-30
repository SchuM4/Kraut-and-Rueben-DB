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
public class BestellungRezeptId implements Serializable {
    private static final long serialVersionUID = -5290430315216241715L;
    @NotNull
    @Column(name = "bestellungsnr", nullable = false)
    private Integer bestellungsnr;

    @NotNull
    @Column(name = "rezeptnr", nullable = false)
    private Integer rezeptnr;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BestellungRezeptId entity = (BestellungRezeptId) o;
        return Objects.equals(this.rezeptnr, entity.rezeptnr) &&
                Objects.equals(this.bestellungsnr, entity.bestellungsnr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rezeptnr, bestellungsnr);
    }

}