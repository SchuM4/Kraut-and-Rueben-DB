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
public class RezeptZutatId implements Serializable {
    private static final long serialVersionUID = -7539328420404454262L;
    @NotNull
    @Column(name = "rezeptnr", nullable = false)
    private Integer rezeptnr;

    @NotNull
    @Column(name = "zutatnr", nullable = false)
    private Integer zutatnr;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RezeptZutatId entity = (RezeptZutatId) o;
        return Objects.equals(this.rezeptnr, entity.rezeptnr) &&
                Objects.equals(this.zutatnr, entity.zutatnr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rezeptnr, zutatnr);
    }

}