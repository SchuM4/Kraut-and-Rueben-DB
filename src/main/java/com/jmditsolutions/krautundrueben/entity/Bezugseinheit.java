package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bezugseinheit", schema = "krautundrueben")
public class Bezugseinheit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bezugseinheitnr", nullable = false)
    private Integer id;

    @Size(max = 20)
    @NotNull
    @Column(name = "einheit", nullable = false, length = 20)
    private String einheit;

}