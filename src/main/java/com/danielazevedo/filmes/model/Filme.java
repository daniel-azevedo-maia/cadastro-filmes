package com.danielazevedo.filmes.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.Year;
@Entity
@Data
public class Filme implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String titulo;

    private String genero;

    private int anoLancamento;

    @ManyToOne
    @JoinColumn(name = "diretor_id")
    private Diretor diretor;

    /*

        private String sinopse; // Breve descrição ou sinopse do filme
        private double avaliacao; // Avaliação do filme

    */
}