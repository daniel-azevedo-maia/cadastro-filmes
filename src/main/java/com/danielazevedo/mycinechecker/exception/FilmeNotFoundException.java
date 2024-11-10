package com.danielazevedo.mycinechecker.exception;

public class FilmeNotFoundException extends RuntimeException {
    public FilmeNotFoundException(String message) {
        super(message);
    }
}
