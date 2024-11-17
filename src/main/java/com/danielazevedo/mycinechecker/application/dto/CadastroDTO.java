package com.danielazevedo.mycinechecker.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CadastroDTO {

    @NotBlank(message = "O nome de usuário não pode estar vazio.")
    @Size(min = 3, max = 50, message = "O nome de usuário deve ter entre 3 e 50 caracteres.")
    private String username;

    @NotBlank(message = "O e-mail não pode estar vazio.")
    @Email(message = "E-mail inválido.")
    private String email;

    @NotBlank(message = "A senha não pode estar vazia.")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    private String password;
}
