package com.danielazevedo.mycinechecker.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class FilmeDTO {
    private Long id;
    private String titulo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "A data assistida é obrigatória")
    private LocalDate dataAssistido;

    private String opiniao;
    private Integer estrelas;

    private String dataAssistidoFormatada;
}

