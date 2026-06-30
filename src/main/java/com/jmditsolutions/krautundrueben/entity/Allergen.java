package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "allergen", schema = "krautundrueben")
public class Allergen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "allergennr", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "bezeichnung", nullable = false, length = 100)
    private String bezeichnung;

}