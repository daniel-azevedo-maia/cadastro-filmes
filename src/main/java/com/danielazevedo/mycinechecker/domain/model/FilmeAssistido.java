package com.danielazevedo.mycinechecker.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "filmes_assistidos")
public class FilmeAssistido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false) // FK para "usuarios"
    private User usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filme_id", nullable = false) // FK para "filmes"
    private Filme filme;

    @Column(nullable = false)
    private LocalDate dataAssistido;

    @Column(nullable = true)
    private String opiniao;

    @Column(nullable = true)
    private Integer estrelas;
}
