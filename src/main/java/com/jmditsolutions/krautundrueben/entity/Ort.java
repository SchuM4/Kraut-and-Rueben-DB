package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ort", schema = "krautundrueben")
public class Ort {
    @Id
    @Size(max = 10)
    @Column(name = "plz", nullable = false, length = 10)
    private String plz;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stadt_nr", nullable = false)
    private Stadt stadtNr;

}