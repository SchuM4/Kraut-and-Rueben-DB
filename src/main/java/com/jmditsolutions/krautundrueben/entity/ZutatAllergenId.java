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
public class ZutatAllergenId implements Serializable {
    private static final long serialVersionUID = -7578889744667246160L;
    @NotNull
    @Column(name = "zutatennr", nullable = false)
    private Integer zutatennr;

    @NotNull
    @Column(name = "allergennr", nullable = false)
    private Integer allergennr;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ZutatAllergenId entity = (ZutatAllergenId) o;
        return Objects.equals(this.zutatennr, entity.zutatennr) &&
                Objects.equals(this.allergennr, entity.allergennr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zutatennr, allergennr);
    }

}