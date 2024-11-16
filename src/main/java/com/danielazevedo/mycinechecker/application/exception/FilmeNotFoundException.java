package com.danielazevedo.mycinechecker.application.exception;

public class FilmeNotFoundException extends RuntimeException {
    public FilmeNotFoundException(String message) {
        super(message);
    }
}
