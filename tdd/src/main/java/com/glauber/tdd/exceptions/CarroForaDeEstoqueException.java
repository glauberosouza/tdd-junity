package com.glauber.tdd.exceptions;

public class CarroForaDeEstoqueException extends RuntimeException {
    public CarroForaDeEstoqueException() {
        super("Não têm o carro escolhido em estoque no momento!");
    }

    public CarroForaDeEstoqueException(String message) {
        super(message);
    }
}
