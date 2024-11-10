package com.danielazevedo.mycinechecker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "filmes")
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private LocalDate dataAssistido;

    @Column(nullable = false)
    private String opiniao;

    @Column(nullable = false)
    private Integer estrelas;
}
