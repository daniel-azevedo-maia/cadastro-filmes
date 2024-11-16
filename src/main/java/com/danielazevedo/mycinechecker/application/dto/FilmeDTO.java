package com.danielazevedo.mycinechecker.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class FilmeDTO {

    private Long id;

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres")
    private String titulo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "A data assistida é obrigatória")
    private LocalDate dataAssistido;

    private String opiniao;

    @NotNull(message = "As estrelas são obrigatórias")
    private Integer estrelas;

    public String getDataAssistidoFormatada() {
        return dataAssistido != null ? dataAssistido.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null;
    }
}
