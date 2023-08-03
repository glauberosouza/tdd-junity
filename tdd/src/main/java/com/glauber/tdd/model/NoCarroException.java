package com.glauber.tdd.model;

public class NoCarroException extends RuntimeException {
    public NoCarroException() {
        super("O carro não pode ser nulo!");
    }
}