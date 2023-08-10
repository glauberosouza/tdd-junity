package com.glauber.tdd.exceptions;

public class NoCarroException extends RuntimeException {
    public NoCarroException() {
        super("O carro não pode ser nulo!");
    }

    public NoCarroException(String message) {
        super(message);
    }
}