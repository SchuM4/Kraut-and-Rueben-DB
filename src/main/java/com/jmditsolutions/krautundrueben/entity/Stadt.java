package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "stadt", schema = "krautundrueben")
public class Stadt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stadt_nr", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "stadtname", nullable = false)
    private String stadtname;

}