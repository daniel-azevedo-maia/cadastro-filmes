package com.danielazevedo.filmes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    /*

    private String sinopse; // Breve descrição ou sinopse do filme
    private double avaliacao; // Avaliação do filme

    */
}
