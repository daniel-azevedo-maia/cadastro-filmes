package com.danielazevedo.mycinechecker.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class FilmeDTO {

    private Long id;

    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    @NotNull(message = "Data é obrigatória")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataAssistido;

    @NotBlank(message = "Opinião é obrigatória")
    private String opiniao;

    @NotNull(message = "Estrelas são obrigatórias")
    @Min(1)
    @Max(5)
    private Integer estrelas;

    public String getDataAssistidoFormatada() {
        return dataAssistido != null ? dataAssistido.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "";
    }

}
