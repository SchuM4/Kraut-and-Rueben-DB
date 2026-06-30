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
public class ZutatErnaehrungskategorieId implements Serializable {
    private static final long serialVersionUID = 4290189943291550095L;
    @NotNull
    @Column(name = "zutatennr", nullable = false)
    private Integer zutatennr;

    @NotNull
    @Column(name = "ernaehrungskategorienr", nullable = false)
    private Integer ernaehrungskategorienr;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ZutatErnaehrungskategorieId entity = (ZutatErnaehrungskategorieId) o;
        return Objects.equals(this.ernaehrungskategorienr, entity.ernaehrungskategorienr) &&
                Objects.equals(this.zutatennr, entity.zutatennr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ernaehrungskategorienr, zutatennr);
    }

}