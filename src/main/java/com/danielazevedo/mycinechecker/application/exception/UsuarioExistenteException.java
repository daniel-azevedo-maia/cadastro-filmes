package com.danielazevedo.mycinechecker.application.exception;

public class UsuarioExistenteException extends RuntimeException {

    public UsuarioExistenteException(String message) {
        super("Usuário já cadastro! Informe outro username.");
    }
}
