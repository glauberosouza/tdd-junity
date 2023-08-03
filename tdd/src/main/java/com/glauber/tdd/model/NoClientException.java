package com.glauber.tdd.model;

public class NoClientException extends RuntimeException {
    public NoClientException() {
        super ("Cliente não pode ser nulo!");
    }
}
