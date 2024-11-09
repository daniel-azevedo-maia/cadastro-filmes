package com.danielazevedo.mycinechecker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    private String genero;

    @Column(nullable = false)
    private int anoLancamento;

    @Column
    private String comentario;

    @Column
    private String posterUrl;
}
