package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ernaehrungskategorie", schema = "krautundrueben")
public class Ernaehrungskategorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ernaehrungskategorienr", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "bezeichnung", nullable = false, length = 100)
    private String bezeichnung;

}