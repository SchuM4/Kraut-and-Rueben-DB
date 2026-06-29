package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ort")
public class Ort {

    @Id
    @Column(name = "plz", length = 10)
    private String plz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadt_nr", nullable = false)
    private Stadt stadt;
}
