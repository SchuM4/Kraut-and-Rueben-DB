package com.jmditsolutions.krautundrueben.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "zahlungsinfo", schema = "krautundrueben")
public class Zahlungsinfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zahlungsinfonr", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "zahlungsart", nullable = false, length = 100)
    private String zahlungsart;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "kundennr", nullable = false)
    private Kunde kundennr;

}