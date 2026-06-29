package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "stadt")
public class Stadt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stadt_nr")
    private Integer id;

    @Column(name = "stadtname", nullable = false, length = 255)
    private String stadtname;

    @OneToMany(mappedBy = "stadt")
    private List<Ort> orte = new ArrayList<>();
}
