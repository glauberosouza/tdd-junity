package com.glauber.tdd.exceptions;

public class EstudanteInvalidoException extends RuntimeException {
    public EstudanteInvalidoException(String message) {
        super(message);
    }

    public EstudanteInvalidoException() {
        super("Não foi encontrado o estudante pelo id informado!");
    }
}